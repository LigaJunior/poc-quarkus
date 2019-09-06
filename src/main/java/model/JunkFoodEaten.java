package model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "junk_food_eaten")
@NamedQuery(name = "JunkFoodsEaten.findAll",
        query = "SELECT f FROM JunkFoodEaten f ORDER BY f.name")
public class JunkFoodEaten extends model.abstracts.Entity {
    @Column(length = 40, unique = true)
    private String name;
    private Long foodId;
    private Long sprintId;
    private Long playerId;
    private int amount;

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public JunkFoodEaten() {
        this.setRegistrationDate(LocalDate.now());

    }

    public JunkFoodEaten(Long foodId, Long playerId, int amount) {
        this.foodId = foodId;
        this.playerId = playerId;
        this.amount = amount;
        this.setRegistrationDate(LocalDate.now());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
