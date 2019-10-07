package com.jph.organizer.rest;

import com.jph.organizer.domain.PanelDomain;
import com.jph.organizer.rest.respresentation.Panel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/organizer")
public class OrganizerController {

    @Autowired
    private PanelAccessor panelAccessor;
    @Autowired
    private OrganizerPanelTransformer organizerPanelTransformer;

    @GetMapping("/panels")
    public List<Panel> getPanels() {
        List<PanelDomain> panelDomains = panelAccessor.getPanels();
        List<Panel> panels = organizerPanelTransformer.fromPanelDomains(panelDomains);
        return panels;
    }

    @GetMapping("panels/{id}")
    public Panel getPanel(
            @PathVariable("id") String id) {
        PanelDomain panelDomain = panelAccessor.getPanelById(Integer.valueOf(id));
                Panel panel = organizerPanelTransformer.fromPanelDomain(panelDomain);
                return panel;
    }
}
