package model.ViewModel;

public class SprintRankOfFoodConsumptionVM {
    private SprintVM sprint;
    private Long amount;

    public SprintRankOfFoodConsumptionVM() {
    }

    public SprintRankOfFoodConsumptionVM(SprintVM sprint, Long amount) {
        this.sprint = sprint;
        this.amount = amount;
    }

    public SprintVM getSprint() {
        return sprint;
    }

    public void setSprint(SprintVM sprintName) {
        this.sprint = sprintName;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
