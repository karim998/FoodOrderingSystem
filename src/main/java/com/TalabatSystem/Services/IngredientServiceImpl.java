package com.TalabatSystem.Services;

import com.TalabatSystem.Models.IngrediantCategory;
import com.TalabatSystem.Models.IngrediantsItem;
import com.TalabatSystem.Models.Restaurant;
import com.TalabatSystem.Repositories.IngredientCategoryRepo;
import com.TalabatSystem.Repositories.IngredientItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientsService{

    @Autowired
    private IngredientItemRepo ingredientItemRepo;

    @Autowired
    private IngredientCategoryRepo ingredientCategoryRepo;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngrediantCategory createIngredientsCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngrediantCategory category = new IngrediantCategory();
        category.setRestaurant(restaurant);
        category.setName(name);
        return ingredientCategoryRepo.save(category);
    }

    @Override
    public IngrediantCategory findIngredientsCategoryById(Long id) throws Exception {

        Optional<IngrediantCategory> opt = ingredientCategoryRepo.findById(id);

        if(opt.isEmpty()){
            throw new Exception("Ingredient Category Not Found");
        }
        return opt.get();
    }

    @Override
    public List<IngrediantCategory> findIngredientsCategoryByRestaurantId(long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientCategoryRepo.findByRestaurantId(id);
    }

    @Override
    public IngrediantsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngrediantCategory category = findIngredientsCategoryById(categoryId);

        IngrediantsItem item = new IngrediantsItem();
        item.setName(ingredientName);
        item.setRestaurant(restaurant);
        item.setCategory(category);

        IngrediantsItem ingredient = ingredientItemRepo.save(item);
        category.getIngrediants().add(ingredient);

        return ingredient;
    }

    @Override
    public List<IngrediantsItem> findRestaurantIngredients(Long restaurantId) {
        return ingredientItemRepo.findByRestaurantId(restaurantId);
    }

    @Override
    public IngrediantsItem updateStock(Long id) throws Exception {
        Optional<IngrediantsItem> optionalIngrediantsItem = ingredientItemRepo.findById(id);
        if(optionalIngrediantsItem.isEmpty()){
            throw new Exception("Ingredient Not Found");
        }
        IngrediantsItem ingrediantsItem = optionalIngrediantsItem.get();
        ingrediantsItem.setInStock(!ingrediantsItem.isInStock());
        return ingredientItemRepo.save(ingrediantsItem);
    }
}
