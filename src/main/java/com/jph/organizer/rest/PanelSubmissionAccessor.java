package com.jph.organizer.rest;

import com.jph.organizer.domain.PanelDomain;
import com.jph.organizer.domain.PaperDomain;
import com.jph.organizer.domain.ParticipantDomain;
import com.jph.organizer.domain.ParticipantRoleDomain;
import com.jph.organizer.repository.PanelRepository;
import com.jph.organizer.repository.PaperRepository;
import com.jph.organizer.repository.ParticipantRepository;
import com.jph.organizer.repository.ParticipantRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PanelSubmissionAccessor {
    @Autowired
    private PanelRepository panelRepository;
    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private ParticipantRoleRepository participantRoleRepository;

    public void createPanel(PanelDomain panelDomain, List<PaperDomain> paperDomains, List<ParticipantDomain> participantDomains,
                            List<ParticipantRoleDomain> participantRoleDomains) {
        panelRepository.save(panelDomain);
        participantRepository.saveAll(participantDomains);
        paperRepository.saveAll(paperDomains);
        participantRoleRepository.saveAll(participantRoleDomains);
    }
}
