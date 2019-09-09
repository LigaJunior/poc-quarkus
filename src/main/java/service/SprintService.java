package service;

import model.Player;
import model.RequestModel.SprintRM;
import model.Sprint;
import model.ViewModel.PlayerVM;
import model.ViewModel.SprintVM;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        List<SprintVM> converted = new ArrayList<>();
        source.forEach(s->converted.add(convertSprintToViewModel(s)));
        return converted;
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
        return convertSprintToViewModel(sprint);
    }

    public List<SprintVM> findActiveSprints() {
        Query query = this.entityManager.createNativeQuery("select * from sprint where active = true", Sprint.class);
        List<Sprint> source = query.getResultList();
        List<SprintVM> sprints = new ArrayList<>();
        source.forEach(s->sprints.add(convertSprintToViewModel(s)));
        return sprints;
    }

    public PlayerVM addToSprint(Long playerId, Long sprintId){
        Sprint sprint  = entityManager.find(Sprint.class,sprintId);
        Player player  = entityManager.find(Player.class,playerId);

        player.addSprint(sprint);
        this.entityManager.merge(player);
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
    }

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