package com.jph.organizer.rest.respresentation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.jph.organizer.domain.PaperDomain;

import java.util.ArrayList;
import java.util.List;

public class Participant {

    private Integer participantId;
    private String firstName;
    private String lastName;
    private String status;
    private String institution;
    private String email;
    private String notes;
    private Paper paper;

    @JsonCreator
    public Participant(Integer participantId, String firstName, String lastName, String status, String institution, String email, String notes, Paper paper) {
        this.participantId = participantId;
        this.paper = paper;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.institution = institution;
        this.email = email;
        this.notes = notes;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public Paper getPaper() {
        return paper;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStatus() {
        return status;
    }

    public String getInstitution() {
        return institution;
    }

    public String getEmail() {
        return email;
    }

    public String getNotes() {
        return notes;
    }
}
