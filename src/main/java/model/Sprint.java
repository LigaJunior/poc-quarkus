package model;

import model.abstracts.Entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;


@NamedQuery(name = "Sprints.findAll",
        query = "SELECT f FROM Sprint f ORDER BY f.name",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
@javax.persistence.Entity
@Table(name = "sprint")
public class Sprint extends Entity {
    @Column(length = 100)
    private String name;
    private Boolean active;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long sprintNumber;

    @ManyToMany(mappedBy = "joinedSprints")
    private List<Player> players;

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

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
