package com.jph.organizer.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping
public class PanelController {
    @Autowired
    private PanelSubmissionAccessor panelSubmissionAccessor;

    @Autowired
    private PanelSheetAccessor panelSheetAccessor;

    @GetMapping("/panels/init")
    public void authTest() {
        List panels = panelSheetAccessor.getSheet();
        panels.forEach(panel -> panelSubmissionAccessor.createPanel((HashMap) panel));
    }
}
