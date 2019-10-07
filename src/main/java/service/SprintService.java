package service;

import error.CustomBadRequestException;
import error.CustomNotFoundException;
import model.ConsumptionHistory;
import model.Player;
import model.RequestModel.SprintRM;
import model.Sprint;
import model.ViewModel.*;
import repository.PlayerRepository;
import repository.SprintRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static model.ViewModel.VMConverter.*;

@ApplicationScoped
public class SprintService {

    @Inject
    public SprintService(SprintRepository sprintRepository, PlayerRepository playerRepository) {
        this.entityManager = sprintRepository;
        this.playerRepository = playerRepository;
    }

    private SprintRepository entityManager;
    private PlayerRepository playerRepository;

    public List<SprintVM> findAll() {
        List<Sprint> source = this.entityManager.findAll();
        return convertSprints(source);
    }

    public SprintVM saveOne(SprintRM sprintRM) {
        if (!isSprintValid(sprintRM)) throw new CustomBadRequestException("The given sprint is not valid.");

        inactivateActualSprint();
        Sprint sprint = new Sprint(sprintRM.getName(), sprintRM.getStartDate(), sprintRM.getEndDate(), sprintRM.getSprintNumber());
        this.entityManager.persist(sprint);

        return convertSprint(sprint);
    }

    private void inactivateActualSprint() {
        Optional<Sprint> opLastSprint = this.entityManager.findAll()
                .stream()
                .filter(Sprint::getActive)
                .findFirst();
        opLastSprint.ifPresent(value -> {
            value.setActive(false);
            this.entityManager.merge(value);
        });
    }

    private boolean isSprintValid(SprintRM sprintRM) {
        boolean validationStatus = false;

        boolean isSprintNumberUnique = !Optional.ofNullable(this.entityManager.findBySprintNumber(sprintRM.getSprintNumber()).get(0))
                .isPresent();
        boolean isEndDateAfterStartDate = sprintRM.getEndDate().isAfter(sprintRM.getStartDate());
        boolean isNameNotEmpty = !sprintRM.getName().isEmpty();

        validationStatus = isSprintNumberUnique && isEndDateAfterStartDate && isNameNotEmpty;
        return validationStatus;

    }

    public List<SprintVM> extendActiveSprintDeadLine(ExtendSprintVM endDate) {
        LocalDate localDate = extractLocalDateOf(endDate);

        List<Sprint> sourceList = this.entityManager.findActive();
        Sprint savedSprint = Optional.ofNullable(sourceList.get(0))
                .orElseThrow(() -> new CustomNotFoundException("No active sprint found"));

        if (localDate.isAfter(savedSprint.getEndDate()))
            savedSprint.setEndDate(localDate);

        return convertSprints(sourceList);
    }

    private LocalDate extractLocalDateOf(ExtendSprintVM endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(endDate.getNewDeadLine(), formatter);
    }

    public PlayerVM addPlayer(Long playerId, Long sprintId) {
        Sprint sprint = Optional.ofNullable(this.entityManager.findById(sprintId)).orElseThrow(() -> new CustomNotFoundException("Sprint not found"));
        Player player = Optional.ofNullable(this.playerRepository.findById(playerId).get(0)).orElseThrow(() -> new CustomNotFoundException("Player not found"));

        player.addSprint(sprint);
        this.playerRepository.merge(player);
        return convertPlayer(player);
    }

    public List<SprintVM> findActiveSprints() {
        List<Sprint> source = this.entityManager.findActive();
        return convertSprints(source);
    }

    public List<SprintPlayerRankedVM> getPlayerRankForActiveSprint() {
        List<SprintPlayerRankedVM> ranking = new ArrayList<>();

        List<Sprint> activeSprintQuery = this.entityManager.findActive();
        Optional<Sprint> opActiveSprint = Optional.ofNullable(activeSprintQuery.get(0));
        if (opActiveSprint.isPresent()) {
            Sprint activeSprint = opActiveSprint.get();
            List<Player> players = activeSprint.getPlayers().stream().filter(p -> p.isActive()).collect(Collectors.toList());
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
        return this.entityManager.findMostJunk();
    }

    public List<SprintRankOfFoodConsumptionVM> getSprintRank() {
        ArrayList<SprintRankOfFoodConsumptionVM> foodConsumptionRanking = new ArrayList<SprintRankOfFoodConsumptionVM>();

        List<Object[]> results = this.entityManager.findSprintRanking();

        results.forEach((record) -> {
            SprintVM sprint = new SprintVM(
                    ((BigInteger) record[0]).longValue(),
                    String.valueOf(record[4]),
                    (Boolean) record[2],
                    LocalDate.parse(String.valueOf(record[6])),
                    LocalDate.parse(String.valueOf(record[3])),
                    ((BigInteger) record[5]).longValue(),
                    LocalDate.parse(String.valueOf(record[1]))
            );
            Long amount = ((BigDecimal) record[7]).longValue();
            foodConsumptionRanking.add(new SprintRankOfFoodConsumptionVM(sprint, amount));

        });
        return foodConsumptionRanking;
    }

    public SprintVM findById(Long id) {
        Sprint sprint = Optional.ofNullable(this.entityManager.findById(id)).orElseThrow(() -> new CustomNotFoundException("Sprint not found"));
        return convertSprint(sprint);
    }
}


