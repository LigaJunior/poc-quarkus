package model.ViewModel;

import java.time.LocalDate;

public class ChocoBoxVM {
    private long id;
    private String name;
    private String reason;
    private Long playerId;
    private Boolean paidOut;
    private LocalDate paidOutDate;
    private LocalDate registrationDate;

    public ChocoBoxVM(long id, String name, String reason, Long playerId, Boolean paidOut, LocalDate paidOutDate, LocalDate registrationDate) {
        this.id = id;
        this.name = name;
        this.reason = reason;
        this.playerId = playerId;
        this.paidOut = paidOut;
        this.paidOutDate = paidOutDate;
        this.registrationDate = registrationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Boolean getPaidOut() {
        return paidOut;
    }

    public void setPaidOut(Boolean paidOut) {
        this.paidOut = paidOut;
    }

    public LocalDate getPaidOutDate() {
        return paidOutDate;
    }

    public void setPaidOutDate(LocalDate paidOutDate) {
        this.paidOutDate = paidOutDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}
