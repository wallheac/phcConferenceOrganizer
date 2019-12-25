package com.jph.organizer.rest.representation;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Date;
import java.util.List;

public class Panel {

    private int panelId;
    private String panelName;
    private Participant contact;
    private String type;
    private Boolean accepted;
    private Date dateTime;
    private String location;
    private String cvUrl;
    private String abstractUrl;
    private String notes;
    private Boolean avRequested;
    private String requestor;
    private Date avRequestDate;
    private List<Participant> participants;

    @JsonCreator
    public Panel(int panelId, String panelName, Participant contact, String type, Boolean accepted, Date dateTime,
                 String location, String cvUrl, String abstractUrl, String notes, Boolean avRequested, String requestor,
                 Date avRequestDate, List<Participant> participants) {
        this.panelId = panelId;
        this.panelName = panelName;
        this.contact = contact;
        this.type = type;
        this.accepted = accepted;
        this.dateTime = dateTime;
        this.location = location;
        this.cvUrl = cvUrl;
        this.abstractUrl = abstractUrl;
        this.notes = notes;
        this.avRequested = avRequested;
        this.requestor = requestor;
        this.avRequestDate = avRequestDate;
        this.participants = participants;
    }

    public int getPanelId() {
        return this.panelId;
    }

    public String getPanelName() {
        return panelName;
    }

    public Participant getContact() {
        return contact;
    }

    public String getType() {
        return type;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getLocation() {
        return location;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public String getAbstractUrl() {
        return abstractUrl;
    }

    public String getNotes() {
        return notes;
    }

    public Boolean getAvRequested() {
        return avRequested;
    }

    public String getRequestor() {
        return requestor;
    }

    public Date getAvRequestDate() {
        return avRequestDate;
    }

    public List<Participant> getParticipants() {
        return participants;
    }
}
