package model.ViewModel;

public class ConsumptionHistoryVM {
    private String junkFoodName;

    private long sprintNumber;

    private String playerName;

    private long amount;

    public ConsumptionHistoryVM(String junkFoodName, long sprintNumber, String playerName, long amount) {
        this.junkFoodName = junkFoodName;
        this.sprintNumber = sprintNumber;
        this.playerName = playerName;
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

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
