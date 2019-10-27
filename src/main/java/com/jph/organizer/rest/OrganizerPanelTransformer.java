package com.jph.organizer.rest;

import com.jph.organizer.domain.*;
import com.jph.organizer.rest.respresentation.Panel;
import com.jph.organizer.rest.respresentation.Paper;
import com.jph.organizer.rest.respresentation.Participant;
import com.jph.organizer.rest.respresentation.RoleAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrganizerPanelTransformer {

    @Autowired
    private PaperAccessor paperAccessor;
    @Autowired
    private RoleAccessor roleAccessor;

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
                    paperDomain.getPanelId(), paperDomain.getAccepted());
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
}
