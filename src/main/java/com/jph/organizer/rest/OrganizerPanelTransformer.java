package com.jph.organizer.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jph.organizer.domain.PanelDomain;
import com.jph.organizer.domain.PaperDomain;
import com.jph.organizer.domain.ParticipantDomain;
import com.jph.organizer.rest.respresentation.Panel;
import com.jph.organizer.rest.respresentation.Paper;
import com.jph.organizer.rest.respresentation.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrganizerPanelTransformer {

    @Autowired
    ParticipantAccessor participantAccessor;
    @Autowired
    PaperAccessor paperAccessor;

    public List<Panel> fromPanelDomains(List<PanelDomain> panelDomains) {
        return new ArrayList<>();
    }

    public Panel fromPanelDomain(PanelDomain panelDomain) {
        int panelId = panelDomain.getPanelId();
        List<Participant> participants = panelDomain.getParticipants().stream()
                .map(participant -> mapParticipant(participant, panelId)).collect(Collectors.toList());

        return mapPanel(panelDomain, participants);
    }

    private Panel mapPanel(PanelDomain panelDomain, List<Participant> participants) {
        return new Panel(panelDomain.getPanelId(), panelDomain.getPanelName(), mapParticipant(panelDomain.getContact(), panelDomain.getPanelId()),
                panelDomain.getType(), panelDomain.getAccepted(), panelDomain.getDateTime(), panelDomain.getLocation(),
                panelDomain.getCvUrl(), panelDomain.getAbstractUrl(), panelDomain.getNotes(), panelDomain.getAvRequested(),
                panelDomain.getRequestor(), panelDomain.getAvRequestDate(), participants);
    }

    private Participant mapParticipant(ParticipantDomain participantDomain, int panelId) {
        if (participantDomain != null) {
            Paper paper = mapPaper(matchPaperDomain(panelId, participantDomain.getParticipantId()));
            return new Participant(participantDomain.getParticipantId(), participantDomain.getFirstName(),
                    participantDomain.getLastName(), participantDomain.getStatus(), participantDomain.getInstitution(),
                    participantDomain.getEmail(), participantDomain.getNotes(), paper);
        }
        return null;
    }

    private Paper mapPaper(PaperDomain paperDomain) {
        return new Paper(paperDomain.getPaperId(), paperDomain.getTitle(), paperDomain.getAbstractUrl(),
                paperDomain.getPanelId(), paperDomain.getAccepted());
    }

    private PaperDomain matchPaperDomain(Integer panelId, Integer participantId) {
        List<PaperDomain> paperDomains = paperAccessor
                .getPaperByPanelId(panelId);
        return paperDomains.stream()
                .filter(paperDomain -> paperDomain.getParticipantDomains()
                        .stream()
                        .anyMatch(participantDomain -> participantDomain.getParticipantId() == participantId))
                .findFirst()
                .orElse(null);

    }
}
