package com.jph.organizer.domain;

import javax.persistence.*;

@Entity(name="ParticipantRole")
@Table(name="participant_role")
public class ParticipantRoleDomain {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private Integer participantId;
    private Integer panelId;
    private Enum<PanelPositionDomain> panelPosition;

    public ParticipantRoleDomain() {
    }

    public ParticipantRoleDomain(Integer participantId, Integer panelId, Enum<PanelPositionDomain> panelPosition) {
        this.participantId = participantId;
        this.panelId = panelId;
        this.panelPosition = panelPosition;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    public Integer getPanelId() {
        return panelId;
    }

    public void setPanelId(Integer panelId) {
        this.panelId = panelId;
    }

    public Enum<PanelPositionDomain> getPanelPosition() {
        return panelPosition;
    }

    public void setPanelPosition(Enum<PanelPositionDomain> panelPosition) {
        this.panelPosition = panelPosition;
    }
}
