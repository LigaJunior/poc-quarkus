package service;

import error.CustomBadRequestException;
import error.CustomNotFoundException;
import model.Player;
import model.RequestModel.PlayerRM;
import model.Sprint;
import model.ViewModel.PlayerRankVM;
import model.ViewModel.PlayerVM;
import repository.PlayerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static model.ViewModel.VMConverter.convertPlayer;
import static model.ViewModel.VMConverter.convertPlayers;

@ApplicationScoped
public class PlayerService {

    @Inject
    public PlayerService(PlayerRepository repository) {
        this.playerRepository = repository;
    }

    private PlayerRepository playerRepository;

    public List<Player> findAll() {
        return this.playerRepository.findAll();
    }

    public Player saveOne(PlayerRM playerRM) {
        if (!isValid(playerRM)) throw new CustomBadRequestException("The given player is not valid.");

        Player player = new Player(playerRM.getName());
        this.playerRepository.persist(player);

        return player;
    }

    public List<Player> findUnallocated() {
        List<Player> sourceList = this.playerRepository.findUnallocated();
        return sourceList;
    }

    public List<Player> deleteOne(Long playerId) {
        Player targetPlayer = Optional.ofNullable(this.playerRepository.findById(playerId).get(0))
                .orElseThrow(() -> new CustomNotFoundException("Player not found"));

        targetPlayer.setActive(false);
        targetPlayer.getSprints().removeIf(Sprint::getActive);

        this.playerRepository.merge(targetPlayer);

        return this.playerRepository.findAll();
    }

    public List<PlayerRankVM> getPlayerRank() {
        List<PlayerRankVM> playerRank = new ArrayList<>();

        List<Object[]> results = this.playerRepository.findPlayerListWithConsumption();

        results.forEach((record) -> {
            PlayerVM player = new PlayerVM(
                    ((BigInteger) record[0]).longValue(),
                    String.valueOf(record[2]),
                    LocalDate.parse(String.valueOf(record[1]))
            );
            Long amount = ((BigDecimal) record[4]).longValue();

            playerRank.add(new PlayerRankVM(amount, player));
        });


        return playerRank;
    }

    private boolean isValid(PlayerRM playerRM) {
        boolean validationStatus = false;

        boolean isNameNotEmpty = !playerRM.getName().isEmpty();

        validationStatus = isNameNotEmpty;

        return validationStatus;
    }
}

