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
        Sprint savedSprint = Optional.ofNullable((Sprint) query.getResultList().get(0))
                .orElseThrow(() -> new CustomNotFoundException("No active sprint found"));
            if (localDate.isAfter(savedSprint.getEndDate())) savedSprint.setEndDate(localDate);
        return convertSprints(query.getResultList());
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
            List<Player> players = activeSprint.getPlayers().stream().filter(p->p.isActive()).collect(Collectors.toList());
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
            String sql ="SELECT sprint.*, SUM(c.amount) as amount " +
                        "FROM consumption_history as c, sprint " +
                        "WHERE c.sprint_id = sprint.id " +
                        "GROUP BY sprint.id ORDER BY sum(c.amount) DESC";
            ArrayList<SprintRankOfFoodConsumptionVM> foodConsumptionRanking = new ArrayList<SprintRankOfFoodConsumptionVM>();
            Query query = this.entityManager.createNativeQuery(sql);
            List<Object[]> results =  query.getResultList();

            results.forEach((record) -> {
                SprintVM sprint = new SprintVM(
                                    ((BigInteger)record[0]).longValue(),
                                    String.valueOf(record[4]),
                                    (Boolean) record[2],
                                    LocalDate.parse(String.valueOf(record[6])),
                                    LocalDate.parse(String.valueOf(record[3])),
                                    ((BigInteger)record[5]).longValue(),
                                    LocalDate.parse(String.valueOf(record[1]))
                    );
                Long amount =((BigDecimal)record[7]).longValue();
                foodConsumptionRanking.add(new SprintRankOfFoodConsumptionVM(sprint, amount));

        });
        return foodConsumptionRanking;
    }

    public List<SprintVM> findById(Long id) {
        Query query = this.entityManager.createNativeQuery("select * from sprint where id ="+id, Sprint.class);
        List<Sprint> source = query.getResultList();
        return convertSprints(source);
    }
}


