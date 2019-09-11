package model.ViewModel;

public class SprintRankedJunkFoodVM {
    private String name;
    private Long amount;
    private Long sprintNumber;

    public SprintRankedJunkFoodVM() {
    }

    public SprintRankedJunkFoodVM(String name, Long amount, Long sprintNumber) {
        this.name = name;
        this.amount = amount;
        this.sprintNumber = sprintNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getSprintNumber() {
        return sprintNumber;
    }

    public void setSprintNumber(Long sprintNumber) {
        this.sprintNumber = sprintNumber;
    }
}
