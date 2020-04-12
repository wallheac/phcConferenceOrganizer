package com.jph.organizer.rest;

import com.jph.organizer.domain.DateDomain;
import com.jph.organizer.domain.PanelDomain;
import com.jph.organizer.domain.PaperDomain;
import com.jph.organizer.rest.representation.ConstructedPanel;
import com.jph.organizer.rest.representation.Panel;
import com.jph.organizer.rest.representation.Paper;

import com.jph.organizer.rest.representation.TimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
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
    private DateAccessor dateAccessor;
    @Autowired
    private OrganizerPanelTransformer organizerPanelTransformer;
    @Autowired
    private OrganizerPaperTransformer organizerPaperTransformer;
    @Autowired
    private DateTransformer dateTransformer;
    @Autowired
    private OrganizerPaperMutator organizerPaperMutator;

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
            @RequestParam(required = false, defaultValue = "false") String unassigned) {
        Boolean flag = unassigned.equals("true");
        List<PaperDomain> paperDomains = paperAccessor.getPapers(flag);
        List<Paper> papers = organizerPaperTransformer.fromPaperDomains(paperDomains);
        return papers;
    }

    @GetMapping("/dates")
    public List<TimeSlot> getDates() {
        List<DateDomain> dates = dateAccessor.getDates();
        return dateTransformer.fromDateDomains(dates);
    }

    @PostMapping("/paper/{id}")
    public Paper updatePaper(@PathVariable("id") String id,
                             @RequestBody Paper paper
    ) {
        PaperDomain paperDomain = null;
        try {
            paperDomain = organizerPaperTransformer.toPaperDomain(paper);
            organizerPaperMutator.persistPaper(paperDomain, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return organizerPaperTransformer.fromPaperDomain(paperDomain);
    }

    @PostMapping("/constructedpanel")
    public List<Panel> postPanels(@RequestBody List<ConstructedPanel> constructedPanels) {
        List<PanelDomain> panelDomains;
        try {
            panelDomains = organizerPanelTransformer.toPanelDomains(constructedPanels);
        } catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }
        return organizerPanelTransformer.fromPanelDomains(panelDomains);
    }

    @PostMapping("/panel")
    public Panel updatePanel(@RequestBody Panel panel) {
        PanelDomain panelDomain = null;
        try {
            panelDomain = organizerPanelTransformer.toPanelDomain(panel);
        } catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }
        return organizerPanelTransformer.fromPanelDomain(panelDomain);
    }
}
