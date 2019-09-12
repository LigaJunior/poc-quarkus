package model.ViewModel;

public class SprintRankOfFoodConsumptionVM {
    private String sprintName;
    private String food;
    private Long amount;

    public SprintRankOfFoodConsumptionVM() {
    }

    public SprintRankOfFoodConsumptionVM(String sprintName, String food, Long amount) {
        this.sprintName = sprintName;
        this.food = food;
        this.amount = amount;
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
