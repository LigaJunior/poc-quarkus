package service;

import model.RequestModel.SprintRM;
import model.Sprint;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        Optional<Sprint> opLastSprint = Arrays.stream(this.findAll())
                .filter(Sprint::getActive)
                .findFirst();
        opLastSprint.ifPresent(value ->{
            value.setActive(false);
            this.entityManager.merge(value);
        });
        this.entityManager.persist(sprint);
        return sprint;
    }

    public List<Sprint> findActiveSprints() {
        Query query = this.entityManager.createNativeQuery("select * from sprint where active = true", Sprint.class);
        List<Sprint> sprints = query.getResultList();
        return sprints;
    }
}
