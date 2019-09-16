package model.ViewModel;

import javax.persistence.Table;

@Table
public class MostJunkSprintVM {
    private String name;
    private Long amount;
    private Long sprintNumber;

    public MostJunkSprintVM() {
    }

    public MostJunkSprintVM(String name, Long amount, Long sprintnumber) {
        this.name = name;
        this.amount = amount;
        this.sprintNumber = sprintnumber;
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
