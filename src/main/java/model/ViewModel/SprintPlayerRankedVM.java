package model.ViewModel;

public class SprintPlayerRankedVM {
    private PlayerVM player;
    private long amount;

    public PlayerVM getPlayer() {
        return player;
    }

    public void setPlayer(PlayerVM player) {
        this.player = player;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
