package com.jph.organizer.utils;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class GoogleAuthorizationUtility {
    private String APPLICATION_NAME = "Organizer-v1";
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport httpTransport;
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String SHEETS_SCOPES = SheetsScopes.SPREADSHEETS;
    private static final String DRIVE_SCOPES = DriveScopes.DRIVE;
    private Sheets sheets;
    private Drive drive;

    public void authorizeGoogle() {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            Credential credential = authorize(Arrays.asList(SHEETS_SCOPES, DRIVE_SCOPES));
            sheets = new Sheets.Builder(httpTransport, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
            System.out.println("sheets success");
            drive = new Drive.Builder(httpTransport, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
            System.out.println("drive success");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


    public Credential authorize(List<String> scopes) throws Exception {
        // load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(GoogleAuthorizationUtility.class.getResourceAsStream("/client_secrets.json")));
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println(
                    "Enter Client ID and Secret from https://code.google.com/apis/console/?api=plus "
                            + "into plus-cmdline-sample/src/main/resources/client_secrets.json");
            System.exit(1);
        }

        // Set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder()
                .setPort(8081)
                .build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public Sheets getSheets() {
        return sheets;
    }

    public Drive getDrive() {
        return drive;
    }
}
