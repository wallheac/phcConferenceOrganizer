package com.jph.organizer.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name="Panel")
@Table(name="panel")
public class PanelDomain {
    @Id
    @GeneratedValue
    private int panelId;

    private String panelName;
    private Integer contact;
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

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name="panel_participant",
        joinColumns=@JoinColumn(name="panel_id"),
        inverseJoinColumns = @JoinColumn(name="participant_id")
    )
    private List<ParticipantDomain> participants;

    public PanelDomain(String panelName, Integer contact, String type, Boolean accepted, Date dateTime,
                       String location, String cvUrl, String abstractUrl, String notes, Boolean avRequested,
                       String requestor, Date avRequestDate, ParticipantDomain... participants) {
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

    public void addParticipant(ParticipantDomain participantDomain) {
        participants.add(participantDomain);
        participantDomain.getPanels().add(this);
    }

    public void removeParticipant(ParticipantDomain participantDomain) {
        participants.remove(participantDomain);
        participantDomain.getPanels().remove(this);
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

    public Integer getContact() {
        return contact;
    }

    public void setContact(Integer contact) {
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

    public List<ParticipantDomain> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantDomain> participants) {
        this.participants = participants;
    }
}
