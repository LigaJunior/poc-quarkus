package service;

import error.CustomBadRequestException;
import model.JunkFood;
import model.RequestModel.JunkFoodRM;
import model.ViewModel.JunkFoodVM;
import repository.JunkFoodRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

import static model.ViewModel.VMConverter.convertJunkFood;
import static model.ViewModel.VMConverter.convertJunkFoods;

@ApplicationScoped
public class JunkFoodService {
    @Inject
    public JunkFoodService(JunkFoodRepository junkFoodRepository) {
        this.junkFoodRepository = junkFoodRepository;
    }

    private JunkFoodRepository junkFoodRepository;

    public List<JunkFoodVM> findAll() {
        return convertJunkFoods(this.junkFoodRepository.findAll());
    }

    public JunkFoodVM saveOne(JunkFoodRM foodRM) {
        if (!isValid(foodRM)) throw new CustomBadRequestException("The given junk food is not valid.");

        JunkFood food = new JunkFood(foodRM.getName());
        this.junkFoodRepository.persist(food);

        return convertJunkFood(food);
    }

    private boolean isValid(JunkFoodRM foodRM) {
        boolean validationStatus = false;

        boolean isNameNotEmpty = !foodRM.getName().isEmpty();

        boolean isNameUnique = this.junkFoodRepository.findByName(foodRM.getName()).size() <= 0;

        validationStatus = isNameUnique && isNameNotEmpty;

        return validationStatus;
    }
}
