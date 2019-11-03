package com.jph.organizer.rest.respresentation;

public class Paper {

    private Integer paperId;
    private String title;
    private String abstractUrl;
    private Integer panelId;
    private Boolean accepted;
    private Participant participant;

    public Paper(Integer paperId, String title, String abstractUrl, Integer panelId, Boolean accepted, Participant participant) {
        this.paperId = paperId;
        this.title = title;
        this.abstractUrl = abstractUrl;
        this.panelId = panelId;
        this.accepted = accepted;
        this. participant = participant;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public String getTitle() {
        return title;
    }

    public String getAbstractUrl() {
        return abstractUrl;
    }

    public Integer getPanelId() {
        return panelId;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public Participant getParticipant() {
        return participant;
    }
}
