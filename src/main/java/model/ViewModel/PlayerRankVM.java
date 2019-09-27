package model.ViewModel;

public class PlayerRankVM {
    private Long amount;
    private PlayerVM player;

    public PlayerRankVM() {
    }

    public PlayerRankVM(Long amount, PlayerVM player) {
        this.amount = amount;
        this.player = player;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public PlayerVM getPlayer() {
        return player;
    }

    public void setPlayer(PlayerVM player) {
        this.player = player;
    }
}
