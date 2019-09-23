package com.jph.organizer.rest.googles;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.jph.organizer.utils.GoogleAuthorizationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class SheetAccessor {

    @Autowired
    GoogleAuthorizationUtility authUtility;

    private String PANEL_SHEET_2018_ID = "1Lt13KULBoodVg8FPnW8TYxbSuP8D3APwXKMzkW46MvQ";
    private String PAPER_SHEET_2018_ID = "1O1y1yexcPUUl__eWhYVsQzH6beIyzsgB3gFi0407-KI";

    public List<List<Object>> getPanelSheet() {
        Sheets sheets = getSheets();
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

    public List<List<Object>> getPaperSheet() {
        Sheets sheets = getSheets();
        try {
            ValueRange response = sheets.spreadsheets()
                    .values()
                    .get(PAPER_SHEET_2018_ID, "Sheet1!A:O")
                    .execute();

            return response.getValues();
        } catch (IOException e) {
            System.out.println("error retrieving range from sheet" + PAPER_SHEET_2018_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private Sheets getSheets () {
        Sheets sheets = authUtility.getSheets();
        if (sheets == null) {
            authUtility.authorizeGoogle();
        }
        return authUtility.getSheets();
    }
}
