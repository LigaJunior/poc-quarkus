package service;

import error.CustomBadRequestException;
import error.CustomNotFoundException;
import model.ViewModel.*;
import model.ConsumptionHistory;
import model.Player;
import model.RequestModel.SprintRM;
import model.Sprint;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
<<<<<<< HEAD
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
=======
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static model.ViewModel.VMConverter.*;
>>>>>>> dev-fernando

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

<<<<<<< HEAD
    public List<SprintVM> findActiveSprints() {
        Query query = this.entityManager.createNativeQuery("select * from sprint where active = true", Sprint.class);
        List<Sprint> source = query.getResultList();
        List<SprintVM> sprints = new ArrayList<>();
        source.forEach(s->sprints.add(convertSprintToViewModel(s)));
=======
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
>>>>>>> dev-fernando
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
                        Optional<ConsumptionHistory> opCurrentHistory = player.getHistory().stream()
                                .filter(h -> h.getSprint().getId().equals(activeSprint.getId()))
                                .findFirst();
                        if (!opCurrentHistory.isPresent()) obj.setAmount(0L);
                        ConsumptionHistory currentHistory = opCurrentHistory.get();
                        obj.setAmount(currentHistory.getAmount());
                        return obj;
                    })
                    .sorted(((o1, o2) -> Long.compare(o2.getAmount(), o1.getAmount())))
                    .collect(Collectors.toList());
        }
        return ranking;
    }

    public List<AllPlayerRankedVM> getPlayerRankOfAllSprints(){
        List<AllPlayerRankedVM> playerRank = new ArrayList<>();

        Query query =  this.entityManager.createNativeQuery("SELECT f.player_id, SUM(f.amount) as amount " +
                "FROM consumption_history f GROUP BY player_id ORDER BY  sum(f.amount) DESC");
        List<Object[]> results =  query.getResultList();
        results.forEach((record) -> {
            Long player = ((BigInteger)record[0]).longValue();
            Long amount = ((BigDecimal)record[1]).longValue();

            playerRank.add(new AllPlayerRankedVM(amount, player));
        });


        return playerRank;
    }

    public SprintRankedJunkFoodVM getSprintRankedJunkFood() {
        SprintRankedJunkFoodVM sprintFoodRank = new SprintRankedJunkFoodVM();
        Query query = this.entityManager.createNativeQuery("SELECT sprint.name, sprint.sprintnumber as sprintnumber, SUM(amount) as amount " +
                "FROM consumption_history " +
                "INNER JOIN sprint ON sprint.id = sprint_id " +
                "GROUP BY sprint.name, sprint.sprintnumber ORDER BY  sum(amount) DESC limit 1");
        Object[] results = (Object[]) query.getSingleResult();

        String name = (String) results[0];
        Long sprintNumber = ((BigInteger) results[1]).longValue();
        Long amount = ((BigDecimal) results[2]).longValue();

        sprintFoodRank.setName(name);
        sprintFoodRank.setAmount(amount);
        sprintFoodRank.setSprintNumber(sprintNumber);

        return sprintFoodRank;
    }

    public List<SprintRankOfFoodConsumptionVM> getSprintRankOfFoodConsumption (){
        String sql = "SELECT sprint.name as sprint, " +
                "junk_food.name as food, SUM(amount) as amount " +
                "FROM ((consumption_history " +
                "INNER JOIN sprint ON sprint.id = sprint_id) " +
                "INNER JOIN junk_food ON junk_food.id = junkfood_id) " +
                "WHERE sprint_id = 1 " +
                "GROUP BY sprint.name, junk_food.name ORDER BY sum(amount) DESC";
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

<<<<<<< HEAD
    public List<SprintVM> changeSprintDeadLine(String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(endDate, formatter);
        Query query = this.entityManager.createNativeQuery("select * from sprint where active = true", Sprint.class);
        List<Sprint> source = query.getResultList();
        List<SprintVM> sprints = new ArrayList<>();
        source.forEach(s->sprints.add(convertSprintToViewModel(s)));
        sprints.forEach(s->s.setEndDate(localDate));

        return sprints;
    }
}
=======
}


>>>>>>> dev-fernando
