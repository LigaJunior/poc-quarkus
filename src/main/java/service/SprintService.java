package service;

import model.RequestModel.SprintRM;
import model.Sprint;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class SprintService {

    @Inject
    public SprintService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private EntityManager entityManager;

    public Sprint[] findAll() {
        return this.entityManager.createNamedQuery("Sprints.findAll", Sprint.class)
                .getResultList().toArray(new Sprint[0]);
    }

    public Sprint saveOne(SprintRM sprintRM) {
        Sprint sprint = new Sprint(sprintRM.getName(),sprintRM.getStartDate(),sprintRM.getEndDate(),sprintRM.getSprintNumber());
        this.entityManager.persist(sprint);
        return sprint;
    }
}
