package com.jph.organizer.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="Participant")
@Table(name="participant")
public class ParticipantDomain {
    @Id
    @GeneratedValue
    private Integer participantId;

    @ManyToMany(mappedBy="participantDomains")
    private List<PaperDomain> paperDomains = new ArrayList<>();

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    private String status;

    private String institution;

    private String email;

    private String notes;

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

    @ManyToMany(mappedBy="participants")
    private List<PanelDomain> panels = new ArrayList<>();

    public ParticipantDomain() {
    }

    public ParticipantDomain(String firstName, String lastName, String status, String institution, String email,
                             String notes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.institution = institution;
        this.email = email;
        this.notes = notes;
    }

    public ParticipantDomain(Integer participantId, String firstName, String lastName, String status, String institution, String email,
                             String notes) {
        this.participantId = participantId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.institution = institution;
        this.email = email;
        this.notes = notes;
    }

    public ParticipantRoleDomain getParticipantRoleDomain() {
        return participantRoleDomain;
    }

    public void setParticipantRoleDomain(ParticipantRoleDomain participantRoleDomain) {
        this.participantRoleDomain = participantRoleDomain;
    }

    public void addPaper(PaperDomain paperDomain) {
        paperDomains.add(paperDomain);
    }

    public List<PanelDomain> getPanels() {
        return panels;
    }

    public void setPanels(List<PanelDomain> panels) {
        this.panels = panels;
    }

    public List<PaperDomain> getPaperDomains() {
        return paperDomains;
    }

    public void setPaperDomains(List<PaperDomain> paperDomains) {
        this.paperDomains = paperDomains;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other){
            return true;
        }
        if(other == null || other.getClass() != this.getClass()){
            return false;
        }
        ParticipantDomain participantDomain = (ParticipantDomain) other;

        return(participantDomain.firstName.equals(this.firstName) &&
                participantDomain.lastName.equals(this.lastName) &&
                participantDomain.email.equals(this.email)) &&
                participantDomain.status.equals(this.status) &&
                participantDomain.institution.equals(this.institution);
    }
}


