package com.jph.organizer.domain;

import javax.persistence.*;

@Entity(name="Paper")
@Table(name="paper")
public class PaperDomain {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private Integer participantId;
    private String title;
    private String abstractUrl;
    private Integer panelId;
    private Boolean accepted;

    public PaperDomain() {
    }

    public PaperDomain(Integer participantId, String title, String abstractUrl, Integer panelId, Boolean accepted) {
        this.participantId = participantId;
        this.title = title;
        this.abstractUrl = abstractUrl;
        this.panelId = panelId;
        this.accepted = accepted;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractUrl() {
        return abstractUrl;
    }

    public void setAbstractUrl(String abstractUrl) {
        this.abstractUrl = abstractUrl;
    }

    public Integer getPanelId() {
        return panelId;
    }

    public void setPanelId(Integer panelId) {
        this.panelId = panelId;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
