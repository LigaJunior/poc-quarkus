package service;

<<<<<<< HEAD
=======
import error.CustomBadRequestException;
>>>>>>> dev-fernando
import model.ConsumptionHistory;
import model.JunkFood;
import model.Player;
import model.Sprint;
import model.RequestModel.ConsumptionHistoryRM;
import model.ViewModel.ConsumptionHistoryVM;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ConsumptionHistoryService {
    @Inject
    public ConsumptionHistoryService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    private EntityManager entityManager;


    public List<ConsumptionHistoryVM> findAll(){
        List<ConsumptionHistory> source = entityManager.createQuery("SELECT f FROM ConsumptionHistory f ORDER BY f.player")
                .getResultList();
        List<ConsumptionHistoryVM> consumptionHistoryVMList = new ArrayList<>();
        source.stream().forEach(c-> consumptionHistoryVMList.add(
                new ConsumptionHistoryVM(c.getJunkFood().getName(),c.getSprint().getSprintNumber(),c.getPlayer().getName(),
                        c.getAmount())
        ));
        return consumptionHistoryVMList;
    }

    public ConsumptionHistoryVM saveOne(ConsumptionHistoryRM consumptionHistoryRM) {
<<<<<<< HEAD
=======
        if (!isValid(consumptionHistoryRM)) throw new CustomBadRequestException("The given consumption history is not valid.");
>>>>>>> dev-fernando
        JunkFood food = this.entityManager.find(JunkFood.class,consumptionHistoryRM.getJunkfoodId());
        Sprint sprint = this.entityManager.find(Sprint.class,consumptionHistoryRM.getSprintId());
        Player player = this.entityManager.find(Player.class,consumptionHistoryRM.getPlayerId());
        ConsumptionHistory consumption = new ConsumptionHistory(food,sprint,player, consumptionHistoryRM.getAmount());
        this.entityManager.persist(consumption);
        return new ConsumptionHistoryVM(consumption.getJunkFood().getName(),
                consumption.getSprint().getSprintNumber(),
                consumption.getPlayer().getName(),
                consumption.getAmount());
    }
<<<<<<< HEAD
=======

    private boolean isValid(ConsumptionHistoryRM consumptionHistoryRM) {
        boolean validationStatus = false;

        boolean isJunkFoodIdPointingToAExistingJunkFood = this.entityManager.createNativeQuery("select * from junk_food where id =" + consumptionHistoryRM.getJunkfoodId() + ";", JunkFood.class)
                .getResultStream()
                .findFirst()
                .isPresent();

        boolean isSprintIdPointingToAExistingSprint = this.entityManager.createNativeQuery("select * from sprint where id =" + consumptionHistoryRM.getSprintId() + ";", Sprint.class)
                .getResultStream()
                .findFirst()
                .isPresent();

        boolean isPlayerIdPointingToAExistingPlayer = this.entityManager.createNativeQuery("select * from player where id =" + consumptionHistoryRM.getPlayerId() + ";", Player.class)
                .getResultStream()
                .findFirst()
                .isPresent();

        boolean amountIsValid = consumptionHistoryRM.getAmount() > 0L;

        validationStatus = isJunkFoodIdPointingToAExistingJunkFood &&
                                isSprintIdPointingToAExistingSprint &&
                                isPlayerIdPointingToAExistingPlayer &&
                                amountIsValid;

        return validationStatus;
    }
>>>>>>> dev-fernando
}
