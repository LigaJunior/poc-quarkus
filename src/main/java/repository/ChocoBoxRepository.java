package repository;

import model.ChocoBox;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class ChocoBoxRepository {
    private EntityManager entityManager;

    @Inject
    public ChocoBoxRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ChocoBox> findAll() {
        return this.entityManager.createNamedQuery("ChocoBox.findAll", ChocoBox.class)
                .getResultList();
    }

    public List<ChocoBox> findById(Long chocoId) {
        Query query = this.entityManager
                .createNativeQuery("SELECT * FROM choco_box WHERE choco_box.id=" + chocoId, ChocoBox.class);
        return query.getResultList();
    }

    public List<ChocoBox> findByPlayerId(Long playerId) {
        Query query = this.entityManager.createNativeQuery("SELECT * from choco_box where playerid=" + playerId +
                " and paidout = false", ChocoBox.class);
        return query.getResultList();
    }

    public void persist(ChocoBox choco) {
        this.entityManager.persist(choco);
    }

    public void merge(ChocoBox targetChoco) {
        this.entityManager.merge(targetChoco);
    }
}
