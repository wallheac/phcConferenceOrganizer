package com.jph.organizer.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity(name="ParticipantRole")
@Table(name="participant_role")
public class ParticipantRoleDomain {

   @EmbeddedId
   private ParticipantRoleIdDomain participantRoleIdDomain;

    private Boolean presenter;

    private Boolean commentator;

    private Boolean chair;

    private Boolean contact;

    public ParticipantRoleDomain() {
    }

    public ParticipantRoleDomain(ParticipantRoleIdDomain participantRoleIdDomain, Boolean presenter, Boolean commentator,
                                 Boolean chair, Boolean contact) {
        this.participantRoleIdDomain = participantRoleIdDomain;
        this.presenter = presenter;
        this.commentator = commentator;
        this.chair = chair;
        this.contact = contact;
    }

    public ParticipantRoleIdDomain getParticipantRoleIdDomain() {
        return participantRoleIdDomain;
    }

    public void setParticipantRoleIdDomain(ParticipantRoleIdDomain participantRoleIdDomain) {
        this.participantRoleIdDomain = participantRoleIdDomain;
    }

    public Boolean getPresenter() {
        return presenter;
    }

    public void setPresenter(Boolean presenter) {
        this.presenter = presenter;
    }

    public Boolean getCommentator() {
        return commentator;
    }

    public void setCommentator(Boolean commentator) {
        this.commentator = commentator;
    }

    public Boolean getChair() {
        return chair;
    }

    public void setChair(Boolean chair) {
        this.chair = chair;
    }

    public Boolean getContact() {
        return contact;
    }

    public void setContact(Boolean contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParticipantRoleDomain)) return false;
        ParticipantRoleDomain that = (ParticipantRoleDomain) o;
        return Objects.equals(presenter, that.presenter) &&
                Objects.equals(commentator, that.commentator) &&
                Objects.equals(chair, that.chair) &&
                Objects.equals(contact, that.contact);
    }

    @Override
    public int hashCode() {

        return Objects.hash(participantRoleIdDomain, presenter, commentator, chair, contact);
    }
}
