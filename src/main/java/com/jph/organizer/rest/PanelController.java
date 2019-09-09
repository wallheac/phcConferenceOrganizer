package com.jph.organizer.rest;

import com.jph.organizer.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Date;

@Controller
@RequestMapping
public class PanelController {
    @Autowired
    private PanelSubmissionAccessor panelSubmissionAccessor;

    @Autowired
    private PanelSheetAccessor panelSheetAccessor;

    //TODO this is for testing. Will need to change
    @GetMapping ("/panels")
    public void savePanel() {
        ParticipantDomain participantDomain = new ParticipantDomain("Amy", "Wall", "Full Professor", "SLU",
                "wallheac@gmail.com", "http://url.com", "abstractURL", "these are notes");
        PanelDomain panelDomain = new PanelDomain("test panels are amazing", participantDomain, "SUBMITTED", true,
                new Date(), "Room 1", "http://cv.com", "http://panelAbstract.com", "these are notes",
                true, "Amy", new Date());
        PaperDomain paperDomain = new PaperDomain(participantDomain.getParticipantId(), "PaperDomain Title", "paperAbstract1.com", panelDomain.getPanelId(), true);
        ParticipantRoleDomain participantRoleDomain = new ParticipantRoleDomain(null, null, PanelPositionDomain.PRESENTER.toString());
        ParticipantDomain participantDomain2 = new ParticipantDomain("First", "Last", "Associate Prof",
                "Washington University", "first@phc.com", "http://cv2.com", "abstractUrl2", "notes for participantDomain2");
        PaperDomain paperDomain2 = new PaperDomain(participantDomain2.getParticipantId(), "Title2", "http://paperAbstract2.com", panelDomain.getPanelId(), true);
        ParticipantRoleDomain participantRoleDomain2 = new ParticipantRoleDomain(null , null, PanelPositionDomain.COMMENTATOR.toString());

        panelSubmissionAccessor.createPanel(panelDomain, Arrays.asList(paperDomain, paperDomain2), Arrays.asList(participantDomain, participantDomain2),
                Arrays.asList(participantRoleDomain, participantRoleDomain2));
    }

    @GetMapping("/panels/init")
    public void authTest() {
        panelSheetAccessor.getSheet();
    }
}
