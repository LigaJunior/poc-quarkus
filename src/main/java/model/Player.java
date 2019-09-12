package model;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "player")
@NamedQuery(name = "Players.findAll",
        query = "SELECT f FROM Player f ORDER BY f.name")
public class Player extends model.abstracts. Entity {
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "player", cascade = CascadeType.PERSIST)
    private List<ConsumptionHistory> history;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "sprint_player",
            joinColumns = {@JoinColumn(name = "player_id")},
            inverseJoinColumns = {@JoinColumn(name = "sprint_id")}
    )
    private List<Sprint> sprints = new ArrayList<>();

    public Player() {
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

    public List<Sprint> getSprints() {
        return sprints;
    }

    public void addSprint(Sprint sprint){
        this.sprints.add(sprint);
        sprint.getPlayers().add(this);
    }

    public List<ConsumptionHistory> getHistory() {
        return history;
    }
}
