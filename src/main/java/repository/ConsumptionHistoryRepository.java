package repository;

import model.ConsumptionHistory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class ConsumptionHistoryRepository {
    private EntityManager entityManager;

    @Inject
    public ConsumptionHistoryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ConsumptionHistory> findAll() {
        return this.entityManager.createQuery("SELECT f FROM ConsumptionHistory f ORDER BY f.player")
                .getResultList();
    }

    public List<ConsumptionHistory> findById(Long consumptionId) {
        Query query = this.entityManager
                .createNativeQuery("SELECT * FROM consumption_history WHERE id=" + consumptionId, ConsumptionHistory.class);
        return query.getResultList();
    }

    public void persist(ConsumptionHistory consumptionHistory) {
        this.entityManager.persist(consumptionHistory);
    }

    public void merge(ConsumptionHistory targetConsumptionHistory) {
        this.entityManager.merge(targetConsumptionHistory);
    }
}
