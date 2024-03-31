package com.TalabatSystem.Services;

import com.TalabatSystem.Models.*;
import com.TalabatSystem.Repositories.FoodRepo;
import com.TalabatSystem.Repositories.RestaurantRepo;
import com.TalabatSystem.Requests.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService{

    @Autowired
    private FoodRepo foodRepo;
    private RestaurantRepo restaurantRepo;
    @Override
    public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant) {

        Food food = new Food();
        food.setFoodcategory(category);
        food.setRestaurant(restaurant);
        food.setName(request.getName());
        food.setDescription(request.getDescription());
        food.setPrice(request.getPrice());
        food.setIamges(request.getImages());
        food.setIngrediants(request.getIngrediants());
        food.setSeasonal(request.isSeasional());
        food.setVegeterian(request.isVegitarien());

        Food savedFood = foodRepo.save(food);
        restaurant.getFoods().add(savedFood);

        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepo.save(food);
    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId,
                                        boolean isVegetarien,
                                        boolean isNonveg,
                                        boolean isSeasonal,
                                        String foodCategory) {
        List<Food> foods = foodRepo.findByRestaurantId(restaurantId);

        if(isVegetarien){
            foods = filterByVegetarien(foods, isVegetarien);
        }
        if(isNonveg){
            foods = filterByNonveg(foods, isNonveg);
        }
        if(isSeasonal){
            foods = filterBySeasonal(foods, isSeasonal);
        }
        if(foodCategory !=null && !foodCategory.equals("")){
            foods = filterByCategory(foods, foodCategory);
        }
        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if(food.getFoodcategory()!=null){
                return food.getFoodcategory().getName().equals(foodCategory);
            }
            return false;
                }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal()== isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByNonveg(List<Food> foods, boolean isNonveg) {
        return foods.stream().filter(food -> food.isVegeterian()== false).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarien(List<Food> foods, boolean isVegetarien) {
        return foods.stream().filter(food -> food.isVegeterian() == isVegetarien).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepo.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood = foodRepo.findById(foodId);

        if (optionalFood.isEmpty()){
            throw new Exception("Food Not Exist");
        }
        return optionalFood.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {

        Food food = findFoodById(foodId);
        food.setAvialable(!food.isAvialable());
        return foodRepo.save(food);

    }
}
