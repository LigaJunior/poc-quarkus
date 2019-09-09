package model;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;


@NamedQuery(name = "Sprints.findAll",
        query = "SELECT f FROM Sprint f ORDER BY f.name")
@Entity
@Table(name = "sprint")
public class Sprint extends model.abstracts.Entity {
    @Column(length = 100)
    private String name;
    private Boolean active;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long sprintNumber;

    @ManyToMany(mappedBy = "sprints", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Player> players = new ArrayList<>();

    @OneToMany(mappedBy = "sprint", cascade = CascadeType.PERSIST)
    private List<ConsumptionHistory> history;

    public Sprint(){
        this.active = true;
        this.setRegistrationDate(LocalDate.now());
    }

    public Sprint(String name, LocalDate startDate, LocalDate endDate, Long sprintNumber) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = true;
        this.sprintNumber = sprintNumber;
        this.setRegistrationDate(LocalDate.now());
    }

    public Long getSprintNumber() {
        return sprintNumber;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setSprintNumber(Long sprintNumber) {
        this.sprintNumber = sprintNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        Period intervalPeriod = Period.between(startDate,endDate);
        return intervalPeriod.getDays();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public List<ConsumptionHistory> getHistory() {
        return history;
    }
}
