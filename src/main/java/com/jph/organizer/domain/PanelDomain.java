package com.jph.organizer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class PanelDomain {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String panelName;
    private Integer contact;
    private Enum<TypeDomain> type;
    private Boolean accepted;
    private Date dateTime;
    private String location;
    private String cvUrl;
    private String abstractUrl;
    private String notes;
    private Boolean avRequested;
    private String requestor;
    private Date avRequestDate;

    public PanelDomain(String panelName, Integer contact, Enum<TypeDomain> type, Boolean accepted, Date dateTime, String location, String cvUrl, String abstractUrl, String notes, Boolean avRequested, String requestor, Date avRequestDate) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    public Integer getContact() {
        return contact;
    }

    public void setContact(Integer contact) {
        this.contact = contact;
    }

    public Enum<TypeDomain> getType() {
        return type;
    }

    public void setType(Enum<TypeDomain> type) {
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
}
