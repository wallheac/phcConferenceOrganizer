package com.jph.organizer.rest;

import com.google.api.services.drive.model.File;
import com.jph.organizer.domain.PaperDomain;
import com.jph.organizer.domain.ParticipantDomain;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class PaperTransformer {

    private final String DRIVE_BASE_URL = "https://drive.google.com/drive/u/0/folders/";

    public List<HashMap<String,Object>> fromPapers(List<List<Object>> paperSheetValues, List<File> driveFiles) {
        List<HashMap<String, Object>> papers = new ArrayList<>();
        HashMap<String, String> docMap = mapDriveFiles(driveFiles);
        if(paperSheetValues == null || paperSheetValues.isEmpty()) {
            System.out.println("No Paper Data");
        } else {
            paperSheetValues.remove(0);
            paperSheetValues.forEach(row -> fromPaper(papers, row, docMap));
        }
        return papers;
    }

    private void fromPaper(List<HashMap<String,Object>> papers, List<Object> row, HashMap<String,String> docMap) {
        HashMap<String, Object> paperMap = new HashMap<>();
        try {
            paperMap.put("paper", mapPaperDomain(row, docMap));
            paperMap.put("participants", mapParticipantDomains(row, paperMap, docMap));
        } catch (Exception e) {
            e.printStackTrace();
        }
        papers.add(paperMap);
    }

    private PaperDomain mapPaperDomain(List<Object> row, HashMap<String,String> docMap) {
        if(docMap.get(row.get(14)) == null || docMap.get(row.get(14)).equals("")) {
            System.out.println("failed to map title: " + row.get(14));
        }
        String abstractUrl = DRIVE_BASE_URL + docMap.get(row.get(14).toString());
        return new PaperDomain(row.get(14).toString(),
                abstractUrl, null, false);
    }

    private List<ParticipantDomain> mapParticipantDomains(List<Object> row, HashMap<String,Object> paperMap,
                                                          HashMap<String, String> docMap) {
        List<ParticipantDomain> participants = new ArrayList<>();
        ParticipantDomain participantDomain = new ParticipantDomain(row.get(3).toString(), row.get(4).toString(),
                row.get(5).toString(), row.get(6).toString(), row.get(7).toString(),null);
        participants.add(participantDomain);
        if(row.get(8).toString().equals("Yes")) {
            ParticipantDomain coPresenter = new ParticipantDomain(row.get(9).toString(), row.get(10).toString(),
                    row.get(11).toString(), row.get(12).toString(), row.get(13).toString(), null);
            participants.add(coPresenter);
        }
        return participants;
    }



    private HashMap<String,String> mapDriveFiles(List<File> driveFiles) {
        HashMap<String, String> docMap = new HashMap<>();
        driveFiles.stream()
                .forEach(file -> docMap.put(StringEscapeUtils.unescapeHtml4(file.getName()), file.getId()));
        return docMap;
    }
}
