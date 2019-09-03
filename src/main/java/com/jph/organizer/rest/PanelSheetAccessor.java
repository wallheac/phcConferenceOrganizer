package com.jph.organizer.rest;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.jph.organizer.utils.GoogleAuthorizationUtility;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


@Component
public class PanelSheetAccessor {

    private GoogleAuthorizationUtility authUtility = new GoogleAuthorizationUtility();

    private String PANEL_SHEET_2018_ID = "1Lt13KULBoodVg8FPnW8TYxbSuP8D3APwXKMzkW46MvQ";
    private Sheets sheets;

    public void getSheet() {
        sheets = authUtility.authorizeSheets();
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
                for (List row: values) {
                    System.out.printf("%s, %s, %s, %s", row.get(1), row.get(2), row.get(3), row.get(4));
                }
            }
        } catch (IOException e) {
            System.out.println("error retrieving range from sheet" + PANEL_SHEET_2018_ID);
        }
    }
}
