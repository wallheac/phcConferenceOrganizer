package com.jph.organizer.rest;

import com.google.api.services.drive.model.File;
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

    @Autowired
    private PanelSheetTransformer panelSheetTransformer;

    @Autowired
    private PanelDriveAccessor panelDriveAccessor;

    @GetMapping("/panels/init")
    public void initDatabase() {
        List<List<Object>> sheetValues = panelSheetAccessor.getSheet();
        List<File> driveFiles = panelDriveAccessor.getDocs();
        List<HashMap<String, Object>> panels = panelSheetTransformer.fromPanels(sheetValues, driveFiles);
        panels.forEach(panel -> panelSubmissionAccessor.createPanel((HashMap) panel));
    }
}
