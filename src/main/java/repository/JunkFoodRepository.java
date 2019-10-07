package repository;

import model.JunkFood;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class JunkFoodRepository {
    private EntityManager entityManager;

    @Inject
    public JunkFoodRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public JunkFood findById(Long foodId) {
        return this.entityManager.find(JunkFood.class, foodId);
    }

    public List<JunkFood> findByName(String foodName) {
        Query query = this.entityManager
                .createNativeQuery("select * from junk_food where name ='" + foodName + "';", JunkFood.class);
        return query.getResultList();
    }

    public List<JunkFood> findAll() {
        return this.entityManager.createNamedQuery("JunkFoods.findAll", JunkFood.class)
                .getResultList();
    }

    public void persist(JunkFood food) {
        this.entityManager.persist(food);
    }

    public void merge(JunkFood targetFood) {
        this.entityManager.merge(targetFood);
    }
}
