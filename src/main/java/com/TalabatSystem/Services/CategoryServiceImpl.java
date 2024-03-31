package com.TalabatSystem.Services;

import com.TalabatSystem.Models.Category;
import com.TalabatSystem.Models.Restaurant;
import com.TalabatSystem.Repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public Category createCategory(String name, Long userId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurant);

        return categoryRepo.save(category);
    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long id) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantByUserId(id);
        return categoryRepo.findByRestaurantId(restaurant.getId());
    }

    @Override
    public Category findCategoryById(long id) throws Exception {
        Optional<Category> optionalCategory = categoryRepo.findById(id);

        if(optionalCategory.isEmpty()){
            throw new Exception("Category Not Found");
        }
        return optionalCategory.get();
    }
}
