package service;

import error.CustomBadRequestException;
import model.ConsumptionHistory;
import model.JunkFood;
import model.Player;
import model.RequestModel.ConsumptionHistoryRM;
import model.Sprint;
import model.ViewModel.ConsumptionHistoryVM;
import repository.ConsumptionHistoryRepository;
import repository.JunkFoodRepository;
import repository.PlayerRepository;
import repository.SprintRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static model.ViewModel.VMConverter.convertPlayer;

@ApplicationScoped
public class ConsumptionHistoryService {
    @Inject
    public ConsumptionHistoryService(ConsumptionHistoryRepository consumptionHistoryRepository,
                                     PlayerRepository playerRepository,
                                     JunkFoodRepository junkFoodRepository,
                                     SprintRepository sprintRepository) {
        this.consumptionHistoryRepository = consumptionHistoryRepository;
        this.playerRepository = playerRepository;
        this.junkFoodRepository = junkFoodRepository;
        this.sprintRepository = sprintRepository;
    }

    private ConsumptionHistoryRepository consumptionHistoryRepository;
    private PlayerRepository playerRepository;
    private JunkFoodRepository junkFoodRepository;
    private SprintRepository sprintRepository;


    public List<ConsumptionHistoryVM> findAll() {
        List<ConsumptionHistoryVM> convertedConsumptionHistoryList = new ArrayList<>();

        List<ConsumptionHistory> source = this.consumptionHistoryRepository.findAll();
        source.stream().forEach(c -> convertedConsumptionHistoryList.add(
                new ConsumptionHistoryVM(c.getJunkFood().getName(), c.getSprint().getSprintNumber(), convertPlayer(c.getPlayer()),
                        c.getAmount())
        ));

        return convertedConsumptionHistoryList;
    }

    public ConsumptionHistoryVM saveOne(ConsumptionHistoryRM consumptionHistoryRM) {
        if (!isValid(consumptionHistoryRM))
            throw new CustomBadRequestException("The given consumption history is not valid.");

        JunkFood food = this.junkFoodRepository.findById(consumptionHistoryRM.getJunkfoodId());
        Sprint sprint = this.sprintRepository.findById(consumptionHistoryRM.getSprintId());
        Player player = this.playerRepository.findById(consumptionHistoryRM.getPlayerId()).get(0);

        ConsumptionHistory consumption = new ConsumptionHistory(food, sprint, player, consumptionHistoryRM.getAmount());
        this.consumptionHistoryRepository.persist(consumption);
        return new ConsumptionHistoryVM(consumption.getJunkFood().getName(),
                consumption.getSprint().getSprintNumber(),
                convertPlayer(consumption.getPlayer()),
                consumption.getAmount());
    }

    private boolean isValid(ConsumptionHistoryRM consumptionHistoryRM) {
        boolean validationStatus = false;

        boolean isJunkFoodIdPointingToAExistingJunkFood = Optional.ofNullable(this.junkFoodRepository.findById(consumptionHistoryRM.getJunkfoodId()))
                .isPresent();

        boolean isSprintIdPointingToAExistingSprint = Optional.ofNullable(this.sprintRepository.findById(consumptionHistoryRM.getSprintId()))
                .isPresent();

        boolean isPlayerIdPointingToAExistingPlayer = Optional.ofNullable(this.playerRepository.findById(consumptionHistoryRM.getPlayerId()).get(0))
                .isPresent();

        boolean isAmountValid = consumptionHistoryRM.getAmount() > 0L;

        validationStatus = isJunkFoodIdPointingToAExistingJunkFood &&
                isSprintIdPointingToAExistingSprint &&
                isPlayerIdPointingToAExistingPlayer &&
                isAmountValid;

        return validationStatus;
    }
}
