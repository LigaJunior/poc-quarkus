package model.ViewModel;

import java.time.LocalDate;
import java.util.List;

public class SprintVM {
    private Long id;
    private String name;
    private Boolean active;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long sprintNumber;
    private List<PlayerVM> players;
    private LocalDate registrationDate;

    public SprintVM(Long id, String name, Boolean active, LocalDate startDate, LocalDate endDate, Long sprintNumber, LocalDate registrationDate) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sprintNumber = sprintNumber;
        this.registrationDate = registrationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public Long getSprintNumber() {
        return sprintNumber;
    }

    public void setSprintNumber(Long sprintNumber) {
        this.sprintNumber = sprintNumber;
    }

    public List<PlayerVM> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerVM> players) {
        this.players = players;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}
