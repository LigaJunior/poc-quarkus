package service;

import model.Player;
import model.RequestModel.PlayerRM;
import model.Sprint;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class PlayerService {

    @Inject
    public PlayerService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    private EntityManager entityManager;

    public Player[] findAll() {
        return this.entityManager.createNamedQuery("Players.findAll", Player.class)
                .getResultList().toArray(new Player[0]);
    }

    public Player saveOne(PlayerRM playerRM) {
        Player player = new Player(playerRM.getName());
        this.entityManager.persist(player);
        return player;
    }

    //TODO
    public Player addToSprint(Long sprintId, Long playerId){
        Sprint sprint  = entityManager.find(Sprint.class,sprintId);
        Player player  = entityManager.find(Player.class,playerId);

        player.addToJoinedSprints(sprint);
        this.entityManager.merge(player);

        return player;
    }
}
