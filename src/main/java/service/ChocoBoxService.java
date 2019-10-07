package service;

import error.CustomBadRequestException;
import error.CustomNotFoundException;
import model.ChocoBox;
import model.Player;
import model.RequestModel.ChocoBoxRM;
import model.ViewModel.ChocoBoxVM;
import repository.ChocoBoxRepository;
import repository.PlayerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static model.ViewModel.VMConverter.convertChoco;
import static model.ViewModel.VMConverter.convertChocos;

@ApplicationScoped
public class ChocoBoxService {
    @Inject
    public ChocoBoxService(ChocoBoxRepository repository, PlayerRepository playerRepository) {
        this.chocoBoxRepository = repository;
        this.playerRepository = playerRepository;
    }

    private ChocoBoxRepository chocoBoxRepository;
    private PlayerRepository playerRepository;

    public List<ChocoBoxVM> findAll() {
        List<ChocoBox> source = this.chocoBoxRepository.findAll();
        return convertChocos(source);
    }

    public ChocoBoxVM saveOne(ChocoBoxRM chocoBoxRM) {
        if (!isValid(chocoBoxRM)) throw new CustomBadRequestException("The given chocobox is not valid.");

        Player player = Optional.ofNullable(this.playerRepository.findById(chocoBoxRM.getPlayerId()).get(0))
                .orElseThrow(() -> new CustomNotFoundException("The given chocobox is not valid."));

        ChocoBox choco = new ChocoBox(player.getName(),
                chocoBoxRM.getReason(),
                chocoBoxRM.getPlayerId(),
                false,
                null
        );

        this.chocoBoxRepository.persist(choco);
        return convertChoco(choco);
    }

    private boolean isValid(ChocoBoxRM chocoBoxRM) {
        boolean validationStatus = false;

        boolean isPlayerIdPointingToAExistingPlayer = Optional.ofNullable(this.playerRepository.findById(chocoBoxRM.getPlayerId())).isPresent();

        validationStatus = isPlayerIdPointingToAExistingPlayer;

        return validationStatus;
    }

    public ChocoBoxVM payOldestChocoDebt(Long playerId) {
        List<ChocoBox> oldestChocoBox = this.chocoBoxRepository.findByPlayerId(playerId);

        oldestChocoBox.sort(Comparator.comparing((ChocoBox::getRegistrationDate)).reversed());
        oldestChocoBox.get(0).setPaidOut(true);
        oldestChocoBox.get(0).setPaidOutDate(LocalDate.now());

        return convertChoco(oldestChocoBox.get(0));
    }

    public void payById(Long chocoId) {
        List<ChocoBox> source = this.chocoBoxRepository.findById(chocoId);
        ChocoBox paidChoco = Optional.ofNullable(source.get(0)).orElseThrow(() -> new CustomNotFoundException("Choco box not found"));

        paidChoco.setPaidOut(true);
        paidChoco.setPaidOutDate(LocalDate.now());

        this.chocoBoxRepository.merge(paidChoco);
    }
}
