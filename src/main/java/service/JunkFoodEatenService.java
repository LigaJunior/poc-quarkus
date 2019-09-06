package service;

import model.JunkFoodEaten;
import model.RequestModel.JunkFoodRM;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

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
        JunkFoodEaten food = new JunkFoodEaten(junkFoodEaten.getName(), junkFoodEaten.getSprintId(),
                junkFoodEaten.getPlayerId(), junkFoodEaten.getAmount());
        entityManager.persist(food);
        return food;
    }
}