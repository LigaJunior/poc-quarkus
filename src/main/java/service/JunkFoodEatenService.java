package service;

import model.JunkFood;
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
        JunkFoodEaten food = new JunkFoodEaten(junkFoodEaten.getFoodId(),
                junkFoodEaten.getPlayerId(), junkFoodEaten.getAmount());
        food.setSprintId(findActiveSprints());
        food.setName(findFoods(food.getFoodId()));
        entityManager.persist(food);
        return food;
    }

    public Long findActiveSprints() {
        Query query = this.entityManager.createNativeQuery("select * from sprint where active = true", Sprint.class);
        List<Sprint> source = query.getResultList();
        Long id = source.get(0).getId();



        return id;
    }

    public String findFoods(Long foodId) {
        Query query = this.entityManager.createNativeQuery("select name from junk_food where id = "+foodId+"", JunkFood.class);
        List name = query.getResultList();
        return name.get(0).toString();
    }
}