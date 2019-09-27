package service;

import error.CustomBadRequestException;
import error.CustomNotFoundException;
import model.ViewModel.*;
import model.ConsumptionHistory;
import model.Player;
import model.RequestModel.SprintRM;
import model.Sprint;
import org.hibernate.boot.jaxb.hbm.internal.ImplicitResultSetMappingDefinition;
import org.hibernate.boot.model.source.internal.hbm.ResultSetMappingBinder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
        if (!isSprintValid(sprintRM)) throw new CustomBadRequestException("The given sprint is not valid.");
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

    private boolean isSprintValid(SprintRM sprintRM) {
        boolean validationStatus = false;
        // it only validates if already exists a sprint with the same number in database
        boolean isSprintNumberUnique = !this.entityManager.createNativeQuery("select * from sprint where sprint_number =" + sprintRM.getSprintNumber() + ";", Sprint.class)
                .getResultStream()
                .findFirst()
                .isPresent();
        // it only validates if the given end date is after start date
        boolean isEndDateAfterStartDate = sprintRM.getEndDate().isAfter(sprintRM.getStartDate());
        // it only validates if the name is not empty
        boolean isNameNotEmpty = !sprintRM.getName().isEmpty();

        validationStatus = isSprintNumberUnique && isEndDateAfterStartDate && isNameNotEmpty;

        return validationStatus;

    }
    public List<SprintVM> extendActiveSprintDeadLine(ExtendSprintVM endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(endDate.getNewDeadLine(), formatter);
        Query query = this.entityManager.createNativeQuery("select * from sprint where active = true", Sprint.class);
        List<Sprint> source = query.getResultList();
        if (source.size() <= 0) throw  new CustomNotFoundException("No active sprint found");
        List<SprintVM> sprints = convertSprints(source);
        sprints.forEach(s->{
            if (localDate.isAfter(s.getEndDate())) s.setEndDate(localDate);
        });
        return sprints;
    }

    public PlayerVM addToSprint(Long playerId, Long sprintId){
        Sprint sprint  = Optional.ofNullable(entityManager.find(Sprint.class,sprintId)).orElseThrow(() -> new CustomNotFoundException("Sprint not found"));
        Player player  = Optional.ofNullable(entityManager.find(Player.class,playerId)).orElseThrow(() -> new CustomNotFoundException("Player not found"));

        player.addSprint(sprint);
        this.entityManager.merge(player);
        return convertPlayer(player);
    }

    public List<SprintVM> findActiveSprints() {
        Query query = this.entityManager.createNativeQuery("select * from sprint where active = true", Sprint.class);
        List<Sprint> source = query.getResultList();
        return convertSprints(source);
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
                        long amount = player.getHistory().stream()
                                .filter(h -> h.getSprint().getId().equals(activeSprint.getId()))
                                .mapToLong(ConsumptionHistory::getAmount).sum();
                            obj.setAmount(amount);
                        return obj;
                    })
                    .sorted(((o1, o2) -> Long.compare(o2.getAmount(), o1.getAmount())))
                    .collect(Collectors.toList());
        }
        return ranking;
    }

    public List<MostJunkSprintVM> getMostJunkSprint() {
        TypedQuery<MostJunkSprintVM> getMostJunk = this.entityManager
                .createNamedQuery("getMostJunk", MostJunkSprintVM.class);
        return getMostJunk.getResultList();
    }

    public List<SprintRankOfFoodConsumptionVM> getSprintRank(){
        String sql =
                "SELECT sprint.name as sprint, SUM(amount) as amount" +
                "FROM ((consumption_history" +
                "INNER JOIN sprint ON sprint.id = sprint_id)" +
                "INNER JOIN junk_food ON junk_food.id = junkfood_id)" +
                "GROUP BY sprint.name" +
                "ORDER BY sum(amount) DESC";
            ArrayList<SprintRankOfFoodConsumptionVM> rankFoodOfSprint = new ArrayList<SprintRankOfFoodConsumptionVM>();
            Query query = this.entityManager.createNativeQuery(sql);
            List<Object[]> results =  query.getResultList();

            results.forEach((record) -> {
                String sprintName = (String)record[0];
                String food = (String)record[1];
                Long amount = ((BigDecimal)record[2]).longValue();
                rankFoodOfSprint.add(new SprintRankOfFoodConsumptionVM(sprintName, food, amount));

        });
        return rankFoodOfSprint;
    }

    public List<SprintVM> findById(Long id) {
        Query query = this.entityManager.createNativeQuery("select * from sprint where id ="+id, Sprint.class);
        List<Sprint> source = query.getResultList();
        return convertSprints(source);
    }
}


