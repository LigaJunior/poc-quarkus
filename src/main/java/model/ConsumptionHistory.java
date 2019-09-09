package model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "consumption_history")
public class ConsumptionHistory extends model.abstracts.Entity {
    @ManyToOne
    @JoinColumn(name = "junkfood_id")
    private JunkFood junkFood;

    @ManyToOne
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    private long amount;

    public ConsumptionHistory() {
        this.amount = 0;
        this.setRegistrationDate(LocalDate.now());
    }

    public ConsumptionHistory(JunkFood junkFood, Sprint sprint, Player player, long amount) {
        this.junkFood = junkFood;
        this.sprint = sprint;
        this.player = player;
        this.amount = amount;
        this.setRegistrationDate(LocalDate.now());
    }

    public JunkFood getJunkFood() {
        return junkFood;
    }

    public void setJunkFood(JunkFood junkFood) {
        this.junkFood = junkFood;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
