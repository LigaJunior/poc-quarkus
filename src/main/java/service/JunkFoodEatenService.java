package service;

import model.JunkFoodEaten;
import model.Sprint;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class JunkFoodEatenService
{
    @Inject
    public JunkFoodEatenService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private EntityManager entityManager;

    public JunkFoodEaten[] findAll(){
        return entityManager.createNamedQuery("JunkFoodsEaten.findAll", JunkFoodEaten.class)
                .getResultList().toArray(new JunkFoodEaten[0]);
    }

    public JunkFoodEaten saveOne(JunkFoodEaten junkFoodEaten){
        JunkFoodEaten food = new JunkFoodEaten(junkFoodEaten.getName(),
                junkFoodEaten.getPlayerId(), junkFoodEaten.getAmount());
        food.setSprintId(findActiveSprints());
        entityManager.persist(food);
        return food;
    }

    public Long findActiveSprints() {
        Query query = this.entityManager.createNativeQuery("select * from sprint where active = true", Sprint.class);
        List<Sprint> source = query.getResultList();
        Long id = source.get(0).getId();



        return id;
    }
}