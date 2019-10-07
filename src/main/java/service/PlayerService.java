package service;

import error.CustomBadRequestException;
import error.CustomNotFoundException;
import model.Player;
import model.RequestModel.PlayerRM;
import model.Sprint;
import model.ViewModel.PlayerRankVM;
import model.ViewModel.PlayerVM;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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
    public PlayerService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private EntityManager entityManager;

    public List<PlayerVM> findAll() {
        List<PlayerVM> playersVM = new ArrayList<>();

        List<Player> players = this.entityManager.createNamedQuery("Players.findAll", Player.class)
                .getResultList();
        players.forEach(p -> playersVM.add(convertPlayer(p)));

        return playersVM;
    }

    public PlayerVM saveOne(PlayerRM playerRM) {
        if (!isValid(playerRM)) throw new CustomBadRequestException("The given player is not valid.");

        Player player = new Player(playerRM.getName());
        this.entityManager.persist(player);

        return convertPlayer(player);
    }

    private boolean isValid(PlayerRM playerRM) {
        boolean validationStatus = false;

        boolean isNameNotEmpty = !playerRM.getName().isEmpty();

        validationStatus = isNameNotEmpty;

        return validationStatus;
    }

    public List<PlayerVM> findUnallocated() {
        String sqlQuery = "SELECT * FROM player " +
                "WHERE player.active = true and player.id NOT IN " +
                "(SELECT player_id FROM sprint_player, sprint WHERE sprint_player.sprint_id = sprint.id AND sprint.active=true)";

        @SuppressWarnings("unchecked")
        List<Player> sourceList = this.entityManager.createNativeQuery(sqlQuery, Player.class)
                .getResultList();
        return convertPlayers(sourceList);
    }

    public List<PlayerVM> deleteOne(Long playerId) {
        Query query = this.entityManager
                .createNativeQuery("SELECT * FROM player WHERE player.id=" + playerId, Player.class);
        Player targetPlayer = Optional.ofNullable((Player) query.getResultList().get(0))
                .orElseThrow(() -> new CustomNotFoundException("Player not found"));

        targetPlayer.setActive(false);
        targetPlayer.getSprints().removeIf(Sprint::getActive);

        this.entityManager.merge(targetPlayer);

        return findAll();
    }

    public List<PlayerRankVM> getPlayerRank() {
        List<PlayerRankVM> playerRank = new ArrayList<>();

        Query query = this.entityManager.createNativeQuery(
                "SELECT player.*, SUM(c.amount) as amount " +
                        "FROM consumption_history as c, player " +
                        "WHERE c.player_id = player.id " +
                        "GROUP BY player.id ORDER BY sum(c.amount) DESC"
        );
        List<Object[]> results = query.getResultList();
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
}

