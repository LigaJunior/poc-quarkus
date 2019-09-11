package service;

import model.AllPlayerRanked;
import model.ConsumptionHistory;
import model.Player;
import model.RequestModel.SprintRM;
import model.Sprint;
import model.ViewModel.ExtendSprintVM;
import model.ViewModel.PlayerVM;
import model.ViewModel.SprintPlayerRankedVM;
import model.ViewModel.SprintVM;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static model.ViewModel.VMConverter.*;

@ApplicationScoped
public class SprintService {

    @Inject
    public SprintService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private EntityManager entityManager;

    public List<SprintVM> findAll() {
        List<Sprint> source = this.entityManager.createNamedQuery("Sprints.findAll", Sprint.class)
                .getResultList();
        return convertSprints(source);
    }

    public SprintVM saveOne(SprintRM sprintRM) {
        Sprint sprint = new Sprint(sprintRM.getName(),sprintRM.getStartDate(),sprintRM.getEndDate(),sprintRM.getSprintNumber());
        Optional<Sprint> opLastSprint = this.entityManager.createNamedQuery("Sprints.findAll", Sprint.class)
                .getResultList().stream()
                .filter(Sprint::getActive)
                .findFirst();
        opLastSprint.ifPresent(value ->{
            value.setActive(false);
            this.entityManager.merge(value);
        });
        this.entityManager.persist(sprint);
        return convertSprint(sprint);
    }

    public List<SprintVM> findActiveSprints() {
        Query query = this.entityManager.createNativeQuery("select * from sprint where active = true", Sprint.class);
        List<Sprint> source = query.getResultList();
        return convertSprints(source);
    }

    public List<SprintVM> extendActiveSprintDeadLine(ExtendSprintVM endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(endDate.getNewDeadLine(), formatter);
        Query query = this.entityManager.createNativeQuery("select * from sprint where active = true", Sprint.class);
        List<Sprint> source = query.getResultList();
        List<SprintVM> sprints = convertSprints(source);
        sprints.forEach(s->s.setEndDate(localDate));
        return sprints;
    }

    public PlayerVM addToSprint(Long playerId, Long sprintId){
        Sprint sprint  = entityManager.find(Sprint.class,sprintId);
        Player player  = entityManager.find(Player.class,playerId);

        player.addSprint(sprint);
        this.entityManager.merge(player);
        return convertPlayer(player);
    }



    public List<SprintPlayerRankedVM> getPlayerRankForActiveSprint() {
        List<SprintPlayerRankedVM> ranking = new ArrayList<>();
        List<Sprint> activeSprintQuery = this.entityManager.createNativeQuery("select * from sprint where active = true",Sprint.class).getResultList();
        Optional<Sprint> opActiveSprint = Optional.ofNullable(activeSprintQuery.get(0));
        if (opActiveSprint.isPresent()){
            Sprint activeSprint = opActiveSprint.get();
            List<Player> players = activeSprint.getPlayers();
            ranking = players.stream()
                    .map(player -> {
                        SprintPlayerRankedVM obj = new SprintPlayerRankedVM();
                        obj.setPlayer(convertPlayer(player));
                        obj.setAmount(player.getHistory().stream().mapToLong(ConsumptionHistory::getAmount).sum());
                        return obj;
                    })
                    .sorted(((o1, o2) -> Long.compare(o2.getAmount(), o1.getAmount())))
                    .collect(Collectors.toList());
        }
        return ranking;
    }
    public AllPlayerRanked getPlayerRankOfAllSprints(){
        AllPlayerRanked play = new AllPlayerRanked();

        Query query =  this.entityManager.createNativeQuery("SELECT f.player_id, SUM(f.amount) as amount " +
                "FROM consumption_history f GROUP BY player_id ORDER BY  sum(f.amount) DESC limit 1");
        Object[] results = (Object[]) query.getSingleResult();
        Long player = ((BigInteger)results[0]).longValue();
        Long amount = ((BigDecimal)results[1]).longValue();
        play.setPlayer(player);
        play.setAmount(amount);

//        results.forEach((record) -> {
//            Long player = ((BigInteger)record[0]).longValue();
//            Long amount = ((BigDecimal)record[1]).longValue();
//            play.setPlayer(player);
//            play.setAmount(amount);
//        });

        return play;
    }

}
