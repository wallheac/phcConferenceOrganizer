package com.jph.organizer.rest;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.jph.organizer.utils.GoogleAuthorizationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class PanelSheetAccessor {

    @Autowired
    private PanelSheetTransformer panelSheetTransformer;

    private GoogleAuthorizationUtility authUtility = new GoogleAuthorizationUtility();

    private String PANEL_SHEET_2018_ID = "1Lt13KULBoodVg8FPnW8TYxbSuP8D3APwXKMzkW46MvQ";
    private Sheets sheets;

    public void getSheet() {
        sheets = authUtility.authorizeSheets();
        List<HashMap> panels = new ArrayList();
        try {
            ValueRange response = sheets.spreadsheets()
                    .values()
                    .get(PANEL_SHEET_2018_ID, "Sheet1!A:BB")
                    .execute();
            List<List<Object>> values = response.getValues();
            if(values == null || values.isEmpty()) {
                System.out.println("No Data");
            } else {
                System.out.println("DATA!");
                values.remove(0);
                for (List row: values) {
                    panelSheetTransformer.fromPanel(panels, row);
                }
            }
            System.out.println("done");
        } catch (IOException e) {
            System.out.println("error retrieving range from sheet" + PANEL_SHEET_2018_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(" all done");
    }
}
