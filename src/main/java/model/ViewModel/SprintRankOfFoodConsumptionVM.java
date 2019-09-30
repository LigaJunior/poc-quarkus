package model.ViewModel;

public class SprintRankOfFoodConsumptionVM {
    private String sprintName;
    private Long amount;

    public SprintRankOfFoodConsumptionVM() {
    }

    public SprintRankOfFoodConsumptionVM(String sprintName, Long amount) {
        this.sprintName = sprintName;
        this.amount = amount;
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
