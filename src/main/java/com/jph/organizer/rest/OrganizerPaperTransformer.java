package com.jph.organizer.rest;

import com.jph.organizer.domain.PaperDomain;
import com.jph.organizer.domain.ParticipantDomain;
import com.jph.organizer.rest.representation.Paper;
import com.jph.organizer.rest.representation.Participant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrganizerPaperTransformer {

	@Autowired
	private OrganizerParticipantTransformer organizerParticipantTransformer;

	public List<Paper> fromPaperDomains(List<PaperDomain> paperDomains) {
		return paperDomains.stream().map(this::fromPaperDomain)
				.filter(paperDomain -> !paperDomain.getTitle().isEmpty() && paperDomain.getAbstractUrl() != null)
				.collect(Collectors.toList());
	}

// TODO accommodate a list of authors (this will probably require rethinking your persistence logic
	private Paper fromPaperDomain(PaperDomain paperDomain) {
		Participant participant = null;
		if (paperDomain.getParticipantDomains().size() > 0) {
			participant = organizerParticipantTransformer.fromParticipantDomain(paperDomain.getParticipantDomains().get(0));
		}
		return new Paper(paperDomain.getPaperId(), paperDomain.getTitle(), paperDomain.getAbstractUrl(),
				paperDomain.getPanelId(), paperDomain.getAccepted(), participant);
	}

	public List<PaperDomain> toPaperDomains(List<Paper> papers) {
		return papers.stream().map(this::toPaperDomain).collect(Collectors.toList());
	}

	public PaperDomain toPaperDomain (Paper paper) {
		 return new PaperDomain(paper.getPaperId(), paper.getTitle(),
				paper.getAbstractUrl(), paper.getPanelId(), paper.getAccepted());
	}

}
