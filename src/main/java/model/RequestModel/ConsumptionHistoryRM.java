package model.RequestModel;

public class ConsumptionHistoryRM {
    private long junkfoodId;
    private long sprintId;
    private long playerId;
    private long amount;

    public long getJunkfoodId() {
        return junkfoodId;
    }

    public void setJunkfoodId(long junkfoodId) {
        this.junkfoodId = junkfoodId;
    }

    public long getSprintId() {
        return sprintId;
    }

    public void setSprintId(long sprintId) {
        this.sprintId = sprintId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
