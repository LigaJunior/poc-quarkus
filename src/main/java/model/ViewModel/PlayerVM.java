package model.ViewModel;

<<<<<<< HEAD
=======
import model.Player;

>>>>>>> dev-fernando
import java.time.LocalDate;

public class PlayerVM {
    private Long id;
    private String name;
    private LocalDate registrationDate;

    public PlayerVM(Long id, String name, LocalDate registrationDate) {
        this.name = name;
        this.id = id;
        this.registrationDate = registrationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}
