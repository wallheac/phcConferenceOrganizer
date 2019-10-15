package com.jph.organizer.rest;

import com.jph.organizer.domain.PaperDomain;
import com.jph.organizer.repository.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaperAccessor {

    @Autowired
    private PaperRepository paperRepository;

    public List<PaperDomain> getPapersByPanelId(Integer panelId) {
        return paperRepository.findAllByPanelIdEquals(panelId);

    }

    public List<PaperDomain> getPapers() {
        return paperRepository.findAll();
    }
}
