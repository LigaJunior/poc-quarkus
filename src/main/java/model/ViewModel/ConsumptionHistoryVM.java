package model.ViewModel;

public class ConsumptionHistoryVM {
    private String junkFoodName;

    private long sprintNumber;

    private PlayerVM player;

    private long amount;

    public ConsumptionHistoryVM(String junkFoodName, long sprintNumber, PlayerVM player, long amount) {
        this.junkFoodName = junkFoodName;
        this.sprintNumber = sprintNumber;
        this.player = player;
        this.amount = amount;
    }

    public String getJunkFoodName() {
        return junkFoodName;
    }

    public void setJunkFoodName(String junkFoodName) {
        this.junkFoodName = junkFoodName;
    }

    public long getSprintNumber() {
        return sprintNumber;
    }

    public void setSprintNumber(long sprintNumber) {
        this.sprintNumber = sprintNumber;
    }

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
