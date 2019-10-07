package repository;

import model.Sprint;
import model.ViewModel.MostJunkSprintVM;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class SprintRepository {
    private EntityManager entityManager;

    @Inject
    public SprintRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Sprint findById(Long sprintId) {
        return this.entityManager.find(Sprint.class, sprintId);
    }

    public List<Sprint> findBySprintNumber(Long sprintNumber) {
        Query query = this.entityManager.createNativeQuery("select * from sprint where sprint_number =" + sprintNumber + ";", Sprint.class);
        return query.getResultList();
    }

    public List<Sprint> findActive() {
        Query query = this.entityManager.createNativeQuery("select * from sprint where active = true", Sprint.class);
        return query.getResultList();
    }

    public List<MostJunkSprintVM> findMostJunk() {
        TypedQuery<MostJunkSprintVM> getMostJunk = this.entityManager
                .createNamedQuery("getMostJunk", MostJunkSprintVM.class);
        return getMostJunk.getResultList();
    }

    public List<Object[]> findSprintRanking() {
        String sql = "SELECT sprint.*, SUM(c.amount) as amount " +
                "FROM consumption_history as c, sprint " +
                "WHERE c.sprint_id = sprint.id " +
                "GROUP BY sprint.id ORDER BY sum(c.amount) DESC";
        Query query = this.entityManager.createNativeQuery(sql);
        return query.getResultList();
    }

    public List<Sprint> findAll() {
        return this.entityManager.createNamedQuery("Sprints.findAll", Sprint.class)
                .getResultList();
    }

    public void persist(Sprint sprint) {
        this.entityManager.persist(sprint);
    }

    public void merge(Sprint targetSprint) {
        this.entityManager.merge(targetSprint);
    }
}
