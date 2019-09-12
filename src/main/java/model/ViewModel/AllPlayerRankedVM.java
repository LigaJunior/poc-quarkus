package model.ViewModel;


//@SqlResultSetMapping(
//        name = "AllPlayerRanked",
//        entities = @EntityResult(
//                entityClass = ConsumptionHistory.class,
//                fields = {
//                        @FieldResult(name = "amount", column = "playerid"),
//                        @FieldResult(name = "player", column = "amount")}))
public class AllPlayerRankedVM {
    private Long amount;
    private Long player;

    public AllPlayerRankedVM() {
    }

    public AllPlayerRankedVM(Long amount, Long player) {
        this.amount = amount;
        this.player = player;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getPlayer() {
        return player;
    }

    public void setPlayer(Long player) {
        this.player = player;
    }
}
