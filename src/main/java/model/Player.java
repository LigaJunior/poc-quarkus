package model;

import model.abstracts.Entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity
@Table(name = "player")
@NamedQuery(name = "Players.findAll",
        query = "SELECT f FROM Player f ORDER BY f.name",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") )
public class Player extends Entity {
    private String name;

    @ManyToMany
    @JoinTable(name="player_sprints")
    private List<Sprint> joinedSprints;

    public Player() {
        this.joinedSprints = new ArrayList<>();
        this.setRegistrationDate(LocalDate.now());
    }

    public Player(String name) {
        this.name = name;
        this.setRegistrationDate(LocalDate.now());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sprint> getJoinedSprints() {
        return joinedSprints;
    }

    public void setJoinedSprints(List<Sprint> joinedSprints) {
        this.joinedSprints = joinedSprints;
    }

    public void addToJoinedSprints(Sprint newSprint) {
        this.joinedSprints.add(newSprint);
    }
}
