package service;

import model.Player;
import model.RequestModel.PlayerRM;
import model.Sprint;
import model.ViewModel.PlayerVM;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PlayerService {

    @Inject
    public PlayerService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    private EntityManager entityManager;

    public List<PlayerVM> findAll() {
        List<Player> players = this.entityManager.createNamedQuery("Players.findAll", Player.class)
                .getResultList();
        List<PlayerVM> playersVM = new ArrayList<>();
        players.forEach(p-> playersVM.add(convertPlayerToViewModel(p)));
        return playersVM;
    }

    public PlayerVM saveOne(PlayerRM playerRM) {
        Player player = new Player(playerRM.getName());
        this.entityManager.persist(player);
        return convertPlayerToViewModel(player);
    }

    private PlayerVM convertPlayerToViewModel(Player player){
        return new PlayerVM(player.getId(),player.getName(),player.getRegistrationDate());
    }
}
