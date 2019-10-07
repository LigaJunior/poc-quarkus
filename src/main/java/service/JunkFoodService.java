package service;

import error.CustomBadRequestException;
import model.JunkFood;
import model.RequestModel.JunkFoodRM;
import model.ViewModel.JunkFoodVM;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static model.ViewModel.VMConverter.convertJunkFood;
import static model.ViewModel.VMConverter.convertJunkFoods;

@ApplicationScoped
public class JunkFoodService {
    @Inject
    public JunkFoodService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private EntityManager entityManager;

    public List<JunkFoodVM> findAll() {
        return convertJunkFoods(
                entityManager.createNamedQuery("JunkFoods.findAll", JunkFood.class)
                        .getResultList()
        );
    }

    public JunkFoodVM saveOne(JunkFoodRM foodRM) {
        if (!isValid(foodRM)) throw new CustomBadRequestException("The given junk food is not valid.");
        JunkFood food = new JunkFood(foodRM.getName());
        entityManager.persist(food);
        return convertJunkFood(food);
    }

    private boolean isValid(JunkFoodRM foodRM) {
        boolean validationStatus = false;

        //it only validates if the given name is not empty
        boolean isNameNotEmpty = !foodRM.getName().isEmpty();

        //it only validates if the given name is unique
        boolean isNameUnique = !this.entityManager.createNativeQuery("select * from junk_food where name ='" + foodRM.getName() + "';", JunkFood.class)
                .getResultStream()
                .findFirst()
                .isPresent();

        validationStatus = isNameUnique && isNameNotEmpty;

        return validationStatus;
    }
}
