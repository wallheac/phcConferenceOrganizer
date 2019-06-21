package com.jph.organizer.domain;

import javax.persistence.*;
import java.util.List;

@Entity(name="Participant")
@Table(name="participant")
public class ParticipantDomain {
    @Id
    @GeneratedValue
    private Integer participantId;
    private String firstName;
    private String lastName;
    private String status;
    private String institution;
    private String email;
    private String cvUrl;
    private String notes;

    @ManyToMany(mappedBy="participants")
    private List<PanelDomain> panels;

    public ParticipantDomain() {
    }

    public ParticipantDomain(String firstName, String lastName, String status, String institution, String email, String cvUrl, String notes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.institution = institution;
        this.email = email;
        this.cvUrl = cvUrl;
        this.notes = notes;
    }

    public List<PanelDomain> getPanels() {
        return panels;
    }

    public void setPanels(List<PanelDomain> panels) {
        this.panels = panels;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
