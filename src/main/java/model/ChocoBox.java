package model;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "choco_box")
@NamedQuery(name = "ChocoBox.findAll",
        query = "SELECT f FROM ChocoBox f ORDER BY f.paidOut")
public class ChocoBox extends model.abstracts.Entity {
    private String name;
    private String reason;
    private Long playerId;
    private Boolean paidOut;
    private LocalDate paidOutDate;

    public ChocoBox() {
        this.setRegistrationDate(LocalDate.now());
    }

    public ChocoBox(String name, String reason, Long playerId, Boolean paidOut, LocalDate paidOutDate) {
        this.setRegistrationDate(LocalDate.now());
        this.name = name;
        this.reason = reason;
        this.playerId = playerId;
        this.paidOut = paidOut;
        this.paidOutDate = paidOutDate;
    }

    public static ChocoBox findById(long playerId) {
        return (ChocoBox) find("playerId", playerId).stream();
    }

    public Boolean getPaidOut() {
        return paidOut;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getPaidOutDate() {
        return paidOutDate;
    }

    public void setPaidOutDate(LocalDate paidOutDate) {
        this.paidOutDate = paidOutDate;
    }

    public void setPaidOut(Boolean paidOut) {
        this.paidOut = paidOut;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

