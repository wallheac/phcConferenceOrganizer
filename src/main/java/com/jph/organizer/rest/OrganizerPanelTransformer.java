package com.jph.organizer.rest;

import com.jph.organizer.domain.*;
import com.jph.organizer.rest.representation.ConstructedPanel;
import com.jph.organizer.rest.representation.Panel;
import com.jph.organizer.rest.representation.Paper;
import com.jph.organizer.rest.representation.Participant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PersistenceException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrganizerPanelTransformer {

    @Autowired
    private PaperAccessor paperAccessor;
    @Autowired
    private RoleAccessor roleAccessor;
    @Autowired
    private PanelAccessor panelAccessor;
    @Autowired
    private OrganizerParticipantTransformer organizerParticipantTransformer;
    @Autowired
    private OrganizerPaperTransformer organizerPaperTransformer;
    @Autowired
    private OrganizerPanelMutator organizerPanelMutator;

    public List<Panel> fromPanelDomains(List<PanelDomain> panelDomains) {
        return panelDomains.stream().map(this::fromPanelDomain).collect(Collectors.toList());
    }

    public Panel fromPanelDomain(PanelDomain panelDomain) {
        int panelId = panelDomain.getPanelId();
        List<PaperDomain> paperDomains = paperAccessor
                .getPapersByPanelId(panelId);
        List<ParticipantRoleDomain> roles = roleAccessor.getParticipantRoleDomainsByPanelId(panelId);
        List<Participant> participants = panelDomain.getParticipants()
                .stream()
                .map(participantDomain -> mapParticipant(participantDomain, paperDomains, roles))
                .collect(Collectors.toList());

        return mapPanel(panelDomain, participants);
    }

    private Participant mapParticipant(ParticipantDomain participantDomain, List<PaperDomain> paperDomains,
                                       List<ParticipantRoleDomain> roles) {
        if (participantDomain != null) {
            List<String> roleList = calculateRoles(participantDomain, roles);
            Paper paper = mapPaper(matchPaperDomain(participantDomain.getParticipantId(), paperDomains));
            return new Participant(participantDomain.getParticipantId(), participantDomain.getFirstName(),
                    participantDomain.getLastName(), participantDomain.getStatus(), participantDomain.getInstitution(),
                    roleList, participantDomain.getEmail(), participantDomain.getNotes(), paper);
        }
        return null;
    }

    private List<String> calculateRoles(ParticipantDomain participantDomain, List<ParticipantRoleDomain> roles) {
        ParticipantRoleDomain participantRoleDomain = roles
                .stream()
                .filter(participantRole -> participantRole.getParticipantRoleIdDomain().getParticipantId().equals(participantDomain.getParticipantId()))
                .findFirst()
                .orElse(null);
        List<String> roleList = new ArrayList<>();
        if (participantRoleDomain != null) {
            if(participantRoleDomain.getChair()) {
                roleList.add(PanelPositionDomain.CHAIR.toString());
            }
            if(participantRoleDomain.getCommentator()) {
                roleList.add(PanelPositionDomain.COMMENTATOR.toString());
            }
            if(participantRoleDomain.getContact()) {
                roleList.add(PanelPositionDomain.CONTACT.toString());
            }
            if(participantRoleDomain.getPresenter()) {
                roleList.add(PanelPositionDomain.PRESENTER.toString());
            }
        }
        return roleList;
    }

    private Panel mapPanel(PanelDomain panelDomain, List<Participant> participants) {
        return new Panel(panelDomain.getPanelId(), panelDomain.getPanelName(), null,
                panelDomain.getType(), panelDomain.getAccepted(), panelDomain.getDateTime(), panelDomain.getLocation(),
                panelDomain.getCvUrl(), panelDomain.getAbstractUrl(), panelDomain.getNotes(), panelDomain.getAvRequested(),
                panelDomain.getRequestor(), panelDomain.getAvRequestDate(), participants);
    }

    private Paper mapPaper(PaperDomain paperDomain) {
        if (paperDomain != null) {
            return new Paper(paperDomain.getPaperId(), paperDomain.getTitle(), paperDomain.getAbstractUrl(),
                    paperDomain.getPanelId(), paperDomain.getAccepted(), null);
        }
        return null;
    }

    private PaperDomain matchPaperDomain(Integer participantId, List<PaperDomain> paperDomains) {
        return paperDomains.stream()
                .filter(paperDomain -> paperDomain.getParticipantDomains()
                        .stream()
                        .anyMatch(participantDomain -> participantDomain.getParticipantId() == participantId))
                .findFirst()
                .orElse(null);
    }
    
    public List<PanelDomain> toPanelDomains(List<ConstructedPanel> constructedPanels) throws PersistenceException {
    	return constructedPanels.stream()
                .map(this::toPanelDomain)
                .collect(Collectors.toList());
    }

	public PanelDomain toPanelDomain(ConstructedPanel constructedPanel) throws PersistenceException {
		PanelDomain panelDomain = new PanelDomain();
		panelDomain.setPanelName(constructedPanel.getTitle());
		panelDomain.setType(TypeDomain.CONSTRUCTED.toString());
		//TODO this should eventually be set in the UI
		panelDomain.setAccepted(true);
		List<Participant> participants = constructedPanel.getPapers().stream()
				.map(paper -> paper.getParticipant())
				.collect(Collectors.toList());
		
		List<ParticipantDomain> participantDomains = organizerParticipantTransformer.toParticipantDomains(participants);
		List<PaperDomain> paperDomains = organizerPaperTransformer.toPaperDomains(constructedPanel.getPapers());

		return organizerPanelMutator.persistConstructedPanel(panelDomain, participantDomains, paperDomains);
	}

	public PanelDomain toPanelDomain(Panel panel) throws PersistenceException {
        PanelDomain panelDomain = new PanelDomain();
        panelDomain.setPanelId(panel.getPanelId());
        panelDomain.setPanelName(panel.getPanelName());
        panelDomain.setType(panel.getType());
        panelDomain.setAccepted(panel.getAccepted());
        panelDomain.setNotes(panel.getNotes());
        panelDomain.setAvRequested(panel.getAvRequested());
        panelDomain.setRequestor(panel.getRequestor());
        if(panel.getAvRequestDate() == null) {
            panelDomain.setAvRequestDate(new Date());
        }
        if(panel.getDateTime() != null) {
            panelDomain.setDateTime(panel.getDateTime());
        }

        List<ParticipantDomain> participantDomains = organizerParticipantTransformer.toParticipantDomains(panel.getParticipants());

        return organizerPanelMutator.persistPanel(panelDomain, participantDomains);
    }
}
