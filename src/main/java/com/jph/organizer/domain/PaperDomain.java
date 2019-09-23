package com.jph.organizer.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="Paper")
@Table(name="paper")
public class PaperDomain {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="paper_id")
    private Integer paperId;

    @ManyToMany(cascade = {
            CascadeType.PERSIST
    })
    @JoinTable(name="paper_participant",
            joinColumns=@JoinColumn(name="paper_id"),
            inverseJoinColumns = @JoinColumn(name="participant_id")
    )
    private List<ParticipantDomain> participantDomains = new ArrayList();
    private String title;
    private String abstractUrl;
    private Integer panelId;
    private Boolean accepted;

    public PaperDomain() {
    }

    public PaperDomain(String title, String abstractUrl, Integer panelId, Boolean accepted) {
        this.title = title;
        this.abstractUrl = abstractUrl;
        this.panelId = panelId;
        this.accepted = accepted;
    }

    public void addParticipant(ParticipantDomain participant) {
        participantDomains.add(participant);
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public List<ParticipantDomain> getParticipantDomains() {
        return participantDomains;
    }

    public void setParticipantDomains(List<ParticipantDomain> participantDomains) {
        this.participantDomains = participantDomains;
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
