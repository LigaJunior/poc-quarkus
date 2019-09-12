package model.ViewModel;

import java.time.LocalDate;

public class JunkFoodVM {
    private long id;
    private String name;
    private LocalDate dataCadastro;

    public JunkFoodVM(long id, String name, LocalDate dataCadastro) {
        this.id = id;
        this.name = name;
        this.dataCadastro = dataCadastro;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
