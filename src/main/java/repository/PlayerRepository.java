package repository;

import model.Player;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class PlayerRepository {
    private EntityManager entityManager;

    @Inject
    public PlayerRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Player> findAll() {
        return this.entityManager.createNamedQuery("Players.findAll", Player.class)
                .getResultList();
    }

    public List<Player> findById(Long playerId) {
        Query query = this.entityManager
                .createNativeQuery("SELECT * FROM player WHERE player.active = true and player.id=" + playerId, Player.class);
        return query.getResultList();
    }

    public void persist(Player player) {
        this.entityManager.persist(player);
    }

    public void merge(Player targetPlayer) {
        this.entityManager.merge(targetPlayer);
    }

    public List findUnallocated() {
        String sqlQuery = "SELECT * FROM player " +
                "WHERE player.active = true and player.id NOT IN " +
                "(SELECT player_id FROM sprint_player, sprint WHERE sprint_player.sprint_id = sprint.id AND sprint.active=true)";

        return this.entityManager.createNativeQuery(sqlQuery, Player.class)
                .getResultList();
    }

    public List findPlayerListWithConsumption() {
        Query sqlQuery = this.entityManager.createNativeQuery(
                "SELECT player.*, SUM(c.amount) as amount " +
                        "FROM consumption_history as c, player " +
                        "WHERE c.player_id = player.id " +
                        "GROUP BY player.id ORDER BY sum(c.amount) DESC"
        );

        return sqlQuery.getResultList();
    }
}
