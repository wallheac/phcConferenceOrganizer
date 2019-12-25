package com.jph.organizer.rest;

import com.jph.organizer.domain.PanelDomain;
import com.jph.organizer.domain.PaperDomain;
import com.jph.organizer.rest.representation.ConstructedPanel;
import com.jph.organizer.rest.representation.Panel;
import com.jph.organizer.rest.representation.Paper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizer")
public class
OrganizerController {

    @Autowired
    private PanelAccessor panelAccessor;
    @Autowired
    private PaperAccessor paperAccessor;
    @Autowired
    private OrganizerPanelTransformer organizerPanelTransformer;
    @Autowired
    private OrganizerPaperTransformer organizerPaperTransformer;

    @GetMapping("/panels")
    public List<Panel> getPanels(
    ) {
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

    @GetMapping("/papers")
    public List<Paper> getPapers(
            @RequestParam(required=false, defaultValue="false") String unassigned ) {
        Boolean flag = unassigned.equals("true");
        List<PaperDomain> paperDomains = paperAccessor.getPapers(flag);
        List<Paper> papers =  organizerPaperTransformer.fromPaperDomains(paperDomains);
        return papers;
    }
    
    @PostMapping("/constructedpanel")
    public void postPanels(@RequestBody List<ConstructedPanel> constructedPanels) {
    	List<PanelDomain> panelDomains = organizerPanelTransformer.toPanelDomains(constructedPanels);
    }
}
