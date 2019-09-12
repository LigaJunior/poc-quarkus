package model;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "junk_food")
@NamedQuery(name = "JunkFoods.findAll",
        query = "SELECT f FROM JunkFood f ORDER BY f.name")
public class JunkFood extends model.abstracts.Entity {
    @Column(length = 40, unique = true, name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "junkFood")
    private List<ConsumptionHistory> history;

    public JunkFood() {
        this.setRegistrationDate(LocalDate.now());
    }
    public JunkFood(String name) {
        this.setRegistrationDate(LocalDate.now());
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ConsumptionHistory> getHistory() {
        return history;
    }
}
