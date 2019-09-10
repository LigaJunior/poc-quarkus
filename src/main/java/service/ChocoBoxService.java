package service;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import model.ChocoBox;
import model.Player;
import model.RequestModel.ChocoBoxRM;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ChocoBoxService{
    @Inject
    public ChocoBoxService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private EntityManager entityManager;

    public ChocoBox[] findAll(){
        return entityManager.createNamedQuery("ChocoBox.findAll", ChocoBox.class)
                .getResultList().toArray(new ChocoBox[0]);
    }

    public ChocoBox saveOne(ChocoBoxRM chocoBoxRM) {
        Player name = Player.findById(chocoBoxRM.getPlayerId());
        ChocoBox choco = new ChocoBox(name.getName(), chocoBoxRM.getAmmount(), chocoBoxRM.getPlayerId(), false);
        entityManager.persist(choco);
        return choco;
    }

public ChocoBox updateChoco(Long playerId){
    PanacheQuery<ChocoBox> choco = ChocoBox.find("playerid = ?1 and paidout = ?2", playerId, false);
    List<ChocoBox> pages = choco.list();


return pages.get(0);
}

}
