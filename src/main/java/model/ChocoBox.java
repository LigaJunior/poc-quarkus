package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.time.LocalDate;
@Entity
@Table(name = "choco_box")
@NamedQuery(name = "ChocoBox.findAll",
        query = "SELECT f FROM ChocoBox f ORDER BY f.name")
public class ChocoBox extends model.abstracts.Entity {
    @Column(length = 40, unique = true)
    private String name;
    private int ammount;
    private Long playerId;
    private Boolean paidOut;

    public int getAmmount() {
        return ammount;
    }

    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }

    public ChocoBox() {
        this.setRegistrationDate(LocalDate.now());
    }
    public ChocoBox(String name, int ammount, Long playerId, Boolean paidOut) {
        this.setRegistrationDate(LocalDate.now());
        this.name = name;
        this.ammount = ammount;
        this.playerId = playerId;
        this.paidOut = paidOut;
    }

    public static ChocoBox findById(long playerId){
        return (ChocoBox) find("playerId", playerId).stream();
    }
    public Boolean getPaidOut() {
        return paidOut;
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

