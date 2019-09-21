package com.jph.organizer.rest;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.jph.organizer.utils.GoogleAuthorizationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class PanelSheetAccessor {

    @Autowired
    GoogleAuthorizationUtility authUtility;

    private String PANEL_SHEET_2018_ID = "1Lt13KULBoodVg8FPnW8TYxbSuP8D3APwXKMzkW46MvQ";

    public List<List<Object>> getSheet() {
        authUtility.authorizeGoogle();
        Sheets sheets = authUtility.getSheets();
        try {
            ValueRange response = sheets.spreadsheets()
                    .values()
                    .get(PANEL_SHEET_2018_ID, "Sheet1!A:BB")
                    .execute();

            return response.getValues();
        } catch (IOException e) {
            System.out.println("error retrieving range from sheet" + PANEL_SHEET_2018_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
