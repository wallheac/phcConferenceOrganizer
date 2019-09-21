package com.jph.organizer.rest;

import com.google.api.services.drive.model.File;
import com.jph.organizer.domain.PanelDomain;
import com.jph.organizer.domain.PaperDomain;
import com.jph.organizer.domain.ParticipantDomain;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PanelSheetTransformer {

    public List<HashMap<String, Object>> fromPanels(List<List<Object>> sheetValues, List<File> driveFiles) {
        List<HashMap<String, Object>> panels = new ArrayList<>();
        if (sheetValues == null || sheetValues.isEmpty()) {
            System.out.println("No Data");
        } else {
            sheetValues.remove(0);
            for (List row : sheetValues) {
                fromPanel(panels, row);
            }
        }
        return panels;
    }

    public List<HashMap<String, Object>> fromPanel(List<HashMap<String, Object>> panels, List row) {
        HashMap<String, Object> panelMap = new HashMap();
        try {
            panelMap.put("panel", mapPanelDomain(row));
            mapParticipantAndPaperDomains(row, panelMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        panels.add(panelMap);
        return panels;
    }

    private PanelDomain mapPanelDomain(List list) {
        String notes = list.size() == 54 ? list.get(53).toString() : null;
        return new PanelDomain(list.get(1).toString(), null, "SUBMITTED", false,
                null, null, "http://cv.com", "http://panelAbstract.com", notes,
                false, null, null);
    }


    private HashMap<String, Object> mapParticipantAndPaperDomains(List list, HashMap panel) throws Exception {
        List organizer = list.size() > 7 ? list.subList(2, 7) : null;
        List chair = list.size() > 7 ? list.subList(7, 12) : null;
        List commentator = list.size() > 43 ? list.subList(43, 48) : null;
        List participants = new ArrayList();
        if (list.size() > 30) {
            if (list.get(30).equals("No")) {
                participants = list.subList(12, 30);
            } else {
                List extraParticipants = list.subList(31, 43);
                participants.addAll(list.subList(12, 30));
                participants.addAll(extraParticipants);
            }
        }
        if (organizer != null) {
            panel.put("organizer", mapParticipantDomain(organizer));
        }
        if (chair != null) {
            panel.put("chair", mapParticipantDomain(chair));
        }
        if (commentator != null) {
            panel.put("commentator", mapParticipantDomain(commentator));
        }
        mapPanelistsAndPapers(participants, panel);

        return panel;
    }

    private HashMap mapPanelistsAndPapers(List participants, HashMap panel) throws Exception {
        List<ParticipantDomain> participantDomains = new ArrayList<>();
        List<PaperDomain> paperDomains = new ArrayList<>();
        if (participants.size() % 6 == 0) {
            for (int i = 0; i < participants.size(); i = i + 6) {
                List participant = participants.subList(i, i + 6);
                participantDomains.add(mapParticipantDomain(participant));
                paperDomains.add(mapPaperDomain(participant));
            }
        } else {
            throw new Exception("error in participant list size");
        }
        panel.put("panelists", participantDomains);
        panel.put("papers", paperDomains);
        return panel;
    }

    private PaperDomain mapPaperDomain(List participant) {
        return new PaperDomain(null, participant.get(5).toString(), null, null, false);
    }

    ParticipantDomain mapParticipantDomain(List particpantInformation) {
        return new ParticipantDomain(particpantInformation.get(0).toString(), particpantInformation.get(1).toString(),
                particpantInformation.get(2).toString(), particpantInformation.get(3).toString(),
                particpantInformation.get(4).toString(), null, null, null);
    }
}
