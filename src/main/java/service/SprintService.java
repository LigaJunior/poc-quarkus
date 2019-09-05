package service;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import model.RequestModel.SprintRM;
import model.Sprint;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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
        Sprint sprint = new Sprint(sprintRM.getName(),sprintRM.getStartDate(),sprintRM.getEndDate(),sprintRM.getActive(),sprintRM.getSprintNumber());
        this.entityManager.persist(sprint);
        return sprint;
    }
//    public Sprint[] findActualSprint() {
//        return this.entityManager.createNamedQuery("Sprints.getActiveSprint", Sprint.class)
//                .getResultList().toArray(new Sprint[0]);
//    }

    public ArrayList<Sprint> findActualSprint() {

        //PanacheQuery<PanacheEntityBase> sprint = Sprint.find("active = ?1", true);
        List sprint = entityManager.createQuery(
                "select s " +
                        "from Sprint s " +
                        "where s.active = :active" )
                .setParameter( "active", true )
                .getResultList();


        System.out.println();
        return sprint;
    }
}
