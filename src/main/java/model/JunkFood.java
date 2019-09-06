package model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "junk_food")
@NamedQuery(name = "JunkFoods.findAll",
        query = "SELECT f FROM JunkFood f ORDER BY f.name")
public class JunkFood extends model.abstracts.Entity {
    @Column(length = 40, unique = true)
    private String name;

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
}
