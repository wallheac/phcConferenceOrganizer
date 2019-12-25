package com.jph.organizer.rest;

import com.jph.organizer.domain.PaperDomain;
import com.jph.organizer.domain.ParticipantDomain;
import com.jph.organizer.rest.representation.Paper;
import com.jph.organizer.rest.representation.Participant;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrganizerPaperTransformer {
    public List<Paper> fromPaperDomains(List<PaperDomain> paperDomains) {
        return paperDomains
                .stream()
                .map(this::fromPaperDomain)
                .filter(paperDomain -> !paperDomain.getTitle().isEmpty() && paperDomain.getAbstractUrl() != null)
                .collect(Collectors.toList());
    }
// TODO accommodate a list of authors (this will probably require rethinking your persistence logic
    private Paper fromPaperDomain(PaperDomain paperDomain) {
    	Participant participant = null;
        if(paperDomain.getParticipantDomains().size() > 0) {
            participant = fromParticipantDomain(paperDomain.getParticipantDomains().get(0));
        }
        return new Paper(paperDomain.getPaperId(), paperDomain.getTitle(), paperDomain.getAbstractUrl(),
                paperDomain.getPanelId(), paperDomain.getAccepted(), participant);
    }

    private Participant fromParticipantDomain(ParticipantDomain participantDomain){
        return new Participant(participantDomain.getParticipantId(), participantDomain.getFirstName(),
                participantDomain.getLastName(), participantDomain.getStatus(), participantDomain.getInstitution(),
                null, participantDomain.getEmail(), participantDomain.getNotes(), null);
    }
	public List<PaperDomain> toPaperDomains(List<Paper> papers) {
		return papers.stream().map(paper -> new PaperDomain(paper.getPaperId(), paper.getTitle(), paper.getAbstractUrl(), paper.getPanelId(), paper.getAccepted())).collect(Collectors.toList());
	}


}
