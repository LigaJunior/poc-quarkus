package service;

<<<<<<< HEAD
import model.ConsumptionHistory;
import model.Player;
import model.RequestModel.SprintRM;
import model.Sprint;
import model.ViewModel.ExtendSprintVM;
import model.ViewModel.PlayerVM;
import model.ViewModel.SprintPlayerRankedVM;
=======
import model.Player;
import model.RequestModel.SprintRM;
import model.Sprint;
import model.ViewModel.PlayerVM;
>>>>>>> dev-fernando
import model.ViewModel.SprintVM;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
<<<<<<< HEAD
import java.util.*;
import java.util.stream.Collectors;

import static model.ViewModel.VMConverter.*;
=======
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
<<<<<<< HEAD
        return convertSprints(source);
=======
        List<SprintVM> converted = new ArrayList<>();
        source.forEach(s->converted.add(convertSprintToViewModel(s)));
        return converted;
>>>>>>> dev-fernando
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
<<<<<<< HEAD
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
=======
        return convertSprintToViewModel(sprint);
    }

    public List<SprintVM> findActiveSprints(String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(endDate, formatter);
        Query query = this.entityManager.createNativeQuery("select * from sprint where active = true", Sprint.class);
        List<Sprint> source = query.getResultList();
        List<SprintVM> sprints = new ArrayList<>();
        source.forEach(s->sprints.add(convertSprintToViewModel(s)));
        sprints.forEach(s->s.setEndDate(localDate));

>>>>>>> dev-fernando
        return sprints;
    }

    public PlayerVM addToSprint(Long playerId, Long sprintId){
        Sprint sprint  = entityManager.find(Sprint.class,sprintId);
        Player player  = entityManager.find(Player.class,playerId);

        player.addSprint(sprint);
        this.entityManager.merge(player);
<<<<<<< HEAD
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
=======
        return convertPlayerToViewModel(player);
    }

    private SprintVM convertSprintToViewModel(Sprint sprint){
        SprintVM convertedSprint = new SprintVM(sprint.getId(),sprint.getName(),sprint.getActive(),sprint.getStartDate(),
                sprint.getEndDate(),sprint.getSprintNumber(),sprint.getRegistrationDate());
        List<PlayerVM> playersVM = new ArrayList<>();
        sprint.getPlayers().forEach(p-> playersVM.add(convertPlayerToViewModel(p)));
        convertedSprint.setPlayers(playersVM);
        return convertedSprint;
    }
    private PlayerVM convertPlayerToViewModel(Player player){
        return new PlayerVM(player.getId(),player.getName(),player.getRegistrationDate());
>>>>>>> dev-fernando
    }
}
