package com.jph.organizer.rest;

import com.jph.organizer.domain.PanelDomain;
import com.jph.organizer.repository.PanelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PanelAccessor {

    @Autowired
    private PanelRepository panelRepository;

    public PanelDomain getPanelById(Integer id) {
        return panelRepository.findByPanelIdEquals(id);
    }

    public List<PanelDomain> getPanels() {
        return panelRepository.findAll();
    }
}
