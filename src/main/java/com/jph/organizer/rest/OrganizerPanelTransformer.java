package com.jph.organizer.rest;

import com.jph.organizer.domain.PanelDomain;
import com.jph.organizer.domain.PaperDomain;
import com.jph.organizer.domain.ParticipantDomain;
import com.jph.organizer.rest.respresentation.Panel;
import com.jph.organizer.rest.respresentation.Paper;
import com.jph.organizer.rest.respresentation.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrganizerPanelTransformer {

    @Autowired
    ParticipantAccessor participantAccessor;
    @Autowired
    PaperAccessor paperAccessor;

    public List<Panel> fromPanelDomains(List<PanelDomain> panelDomains) {
        return panelDomains.stream().map(this::fromPanelDomain).collect(Collectors.toList());
    }

    public Panel fromPanelDomain(PanelDomain panelDomain) {
        int panelId = panelDomain.getPanelId();
        List<PaperDomain> paperDomains = paperAccessor
                .getPapersByPanelId(panelId);
        List<Participant> participants = panelDomain.getParticipants().stream()
                .map(participant -> mapParticipant(participant, paperDomains)).collect(Collectors.toList());
        return mapPanel(panelDomain, participants, paperDomains);
    }

    private Panel mapPanel(PanelDomain panelDomain, List<Participant> participants, List<PaperDomain> paperDomains) {
        Participant contact = mapParticipant(panelDomain.getContact(), paperDomains);
        return new Panel(panelDomain.getPanelId(), panelDomain.getPanelName(), contact,
                panelDomain.getType(), panelDomain.getAccepted(), panelDomain.getDateTime(), panelDomain.getLocation(),
                panelDomain.getCvUrl(), panelDomain.getAbstractUrl(), panelDomain.getNotes(), panelDomain.getAvRequested(),
                panelDomain.getRequestor(), panelDomain.getAvRequestDate(), participants);
    }

    private Participant mapParticipant(ParticipantDomain participantDomain, List<PaperDomain> paperDomains) {
        if (participantDomain != null) {
            Paper paper = mapPaper(matchPaperDomain(participantDomain.getParticipantId(), paperDomains));
            return new Participant(participantDomain.getParticipantId(), participantDomain.getFirstName(),
                    participantDomain.getLastName(), participantDomain.getStatus(), participantDomain.getInstitution(),
                    participantDomain.getEmail(), participantDomain.getNotes(), paper);
        }
        return null;
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
