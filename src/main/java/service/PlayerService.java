package service;

import error.CustomBadRequestException;
import model.Player;
import model.RequestModel.PlayerRM;
import model.ViewModel.PlayerVM;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static model.ViewModel.VMConverter.convertPlayer;

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
        players.forEach(p-> playersVM.add(convertPlayer(p)));
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

        //it only validates if the given name is not empty
        boolean isNameNotEmpty = !playerRM.getName().isEmpty();

        validationStatus = isNameNotEmpty;

        return validationStatus;
    }
}
