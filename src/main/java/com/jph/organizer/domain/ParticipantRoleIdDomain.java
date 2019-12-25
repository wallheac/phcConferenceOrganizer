package com.jph.organizer.domain;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

public class ParticipantRoleIdDomain implements Serializable {
    @Column(name="panel")
    private Integer panelId;

    @Column(name="participant")
    private Integer participantId;

    public ParticipantRoleIdDomain(){}

    public ParticipantRoleIdDomain(Integer panelId, Integer participantId) {
        this.panelId = panelId;
        this.participantId = participantId;
    }

    public Integer getPanelId() {
        return panelId;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public void setPanelId(Integer panelId) {
        this.panelId = panelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParticipantRoleIdDomain)) return false;
        ParticipantRoleIdDomain that = (ParticipantRoleIdDomain) o;
        return Objects.equals(panelId, that.panelId) &&
                Objects.equals(participantId, that.participantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(panelId, participantId);
    }
}
