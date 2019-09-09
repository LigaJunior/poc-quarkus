package model.ViewModel;

public class ChocoBoxVM {

    private int ammount;
    private Long playerId;

    public ChocoBoxVM(int ammount, Long playerId) {
        this.ammount = ammount;
        this.playerId = playerId;
    }

    public int getAmmount() {
        return ammount;
    }

    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }
}
