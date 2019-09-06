package model.RequestModel;

import java.time.LocalDate;
import java.time.Period;

public class SprintRM {
    private String name;
    private Long sprintNumber;
    private LocalDate startDate;
    private LocalDate endDate;

    public Long getSprintNumber() {
        return sprintNumber;
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

    public int getDaysDuration() {
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
}
