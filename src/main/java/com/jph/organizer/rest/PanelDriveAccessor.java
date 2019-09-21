package com.jph.organizer.rest;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.jph.organizer.utils.GoogleAuthorizationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class PanelDriveAccessor {

    @Autowired
    private GoogleAuthorizationUtility authUtility;

    public List<File> getDocs() {
        Drive drive = authUtility.getDrive();
//        pattern for a query by name "name='" + panelDomain.getPanelName() + "'"
//        the below is a query by the parent folder ID ... it lists all the children
        String topLevelFolderQuery = "'0B1oco1Xr9_r3bzdRcFRScFJudTg' in parents";
        FileList files = getFileList(drive, topLevelFolderQuery);
        return (List<File>) files.get("files");

    }

    private FileList getFileList(Drive drive, String query) {
        try {
            FileList files = drive.files().list()
                    .setQ(query)
                    .setSpaces("drive")
                    .setPageSize(150)
                    .setFields("nextPageToken, files(id, name)")
                    .execute();

            return files;

        } catch (IOException e) {
            System.out.println("Panel get docs failed");
            e.printStackTrace();
        }
        return new FileList();
    }
}
