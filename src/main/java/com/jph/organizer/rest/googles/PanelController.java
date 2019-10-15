package com.jph.organizer.rest.googles;

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
    private SubmissionMutator submissionMutator;
    @Autowired
    private SheetAccessor sheetAccessor;
    @Autowired
    private PanelTransformer panelTransformer;
    @Autowired
    private PaperTransformer paperTransformer;
    @Autowired
    private DriveAccessor driveAccessor;

    @GetMapping("/panels/init")
    public void initPanels() {
        List<List<Object>> panelSheetValues = sheetAccessor.getPanelSheet();
        List<File> driveFiles = driveAccessor.getPanelDocs();
        List<HashMap<String, Object>> panels = panelTransformer.fromPanels(panelSheetValues, driveFiles);
        panels.forEach(panel -> submissionMutator.createPanel(panel));
    }

    @GetMapping("/papers/init")
    public void initPapers() {
        List<List<Object>> paperSheetValues = sheetAccessor.getPaperSheet();
        List<File> driveFiles = driveAccessor.getPaperDocs();
        List<HashMap<String, Object>> papers = paperTransformer.fromPapers(paperSheetValues, driveFiles);
        papers.forEach(paper -> submissionMutator.createPaperSubmission((HashMap) paper));
    }
}
