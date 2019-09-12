package service;

import error.CustomBadRequestException;
import model.ChocoBox;
import model.Player;
import model.RequestModel.ChocoBoxRM;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.time.LocalDate;
import java.util.Comparator;
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
        if (!isValid(chocoBoxRM)) throw new CustomBadRequestException("The given chocobox is not valid.");
        Player name = Player.findById(chocoBoxRM.getPlayerId());
        ChocoBox choco = new ChocoBox(name.getName(), chocoBoxRM.getAmount(), chocoBoxRM.getPlayerId(), false, null);
        this.entityManager.persist(choco);
        return choco;
    }

    private boolean isValid(ChocoBoxRM chocoBoxRM) {
        boolean validationStatus = false;

        boolean isPlayerIdPointingToAExistingPlayer = this.entityManager.createNativeQuery("select * from player where id =" + chocoBoxRM.getPlayerId() + ";", Player.class)
                .getResultStream()
                .findFirst()
                .isPresent();

        boolean amountIsValid = chocoBoxRM.getAmount() >= 0L;

        validationStatus = isPlayerIdPointingToAExistingPlayer &&
                                amountIsValid;

        return validationStatus;
    }

    public ChocoBox updateChoco(Long playerId){
        Query query =  this.entityManager.createNativeQuery("SELECT * from choco_box where playerid="+playerId+
                " and paidout = false", ChocoBox.class);
        List<ChocoBox> chocob = query.getResultList();
        chocob.sort(Comparator.comparing((ChocoBox::getRegistrationDate)).reversed());
        chocob.get(0).setPaidOut(true);
        chocob.get(0).setPaidOutDate(LocalDate.now());

    return chocob.get(0);
    }

}
