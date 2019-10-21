package com.jph.organizer.domain;

import javax.persistence.*;
import java.util.Date;

@Entity(name="Panel")
@Table(name="panel")
public class PanelDomain {
    @Id
    @GeneratedValue
    @Column(name="panel_id")
    private int panelId;

    @Column(name="panel_name")
    private String panelName;

    @OneToOne
    private ParticipantDomain contact;

    private String type;

    private Boolean accepted;

    @Column(name="date_time")
    private Date dateTime;

    private String location;

    @Column(name="cv_url")
    private String cvUrl;

    @Column(name="abstract_url")
    private String abstractUrl;

    private String notes;

    @Column(name="av_requested")
    private Boolean avRequested;

    private String requestor;

    @Column(name="av_request_date")
    private Date avRequestDate;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(
                    name = "panel",
                    referencedColumnName = "panel"),
            @JoinColumn(
                    name = "participant",
                    referencedColumnName = "participant")
    })
    private ParticipantRoleDomain participantRoleDomain;

    public PanelDomain() {
    }

    public PanelDomain(String panelName, ParticipantDomain contact, String type, Boolean accepted, Date dateTime,
                       String location, String cvUrl, String abstractUrl, String notes, Boolean avRequested,
                       String requestor, Date avRequestDate, ParticipantDomain... participantDomains) {
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
    }

    public int getPanelId() {
        return panelId;
    }

    public void setPanelId(int panelId) {
        this.panelId = panelId;
    }

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    public ParticipantDomain getContact() {
        return contact;
    }

    public void setContact(ParticipantDomain contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public String getAbstractUrl() {
        return abstractUrl;
    }

    public void setAbstractUrl(String abstractUrl) {
        this.abstractUrl = abstractUrl;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getAvRequested() {
        return avRequested;
    }

    public void setAvRequested(Boolean avRequested) {
        this.avRequested = avRequested;
    }

    public String getRequestor() {
        return requestor;
    }

    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }

    public Date getAvRequestDate() {
        return avRequestDate;
    }

    public void setAvRequestDate(Date avRequestDate) {
        this.avRequestDate = avRequestDate;
    }

    public ParticipantRoleDomain getParticipantRoleDomain() {
        return this.participantRoleDomain;
    }

    public void setParticipantRoleDomain(ParticipantRoleDomain participantRoleDomain) {
        this.participantRoleDomain = participantRoleDomain;
    }
}
