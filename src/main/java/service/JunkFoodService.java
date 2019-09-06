package service;

import model.JunkFood;
import model.RequestModel.JunkFoodRM;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class JunkFoodService
{
    @Inject
    public JunkFoodService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private EntityManager entityManager;

    public JunkFood[] findAll(){
        return entityManager.createNamedQuery("JunkFoods.findAll", JunkFood.class)
                .getResultList().toArray(new JunkFood[0]);
    }

    public JunkFood saveOne(JunkFoodRM foodRM){
        JunkFood food = new JunkFood(foodRM.getName());
        entityManager.persist(food);
        return food;
    }
}
