package com.TalabatSystem.Services;

import com.TalabatSystem.Models.Category;
import com.TalabatSystem.Models.Food;
import com.TalabatSystem.Models.Restaurant;
import com.TalabatSystem.Models.User;
import com.TalabatSystem.Requests.CreateFoodRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FoodService {
    public Food createFood(CreateFoodRequest request, Category category , Restaurant restaurant);

    void  deleteFood(Long foodId) throws Exception;

    //this code help us to filter food by these types in the parameter
    public List<Food> getRestaurantsFood(Long restaurantId,
                                        boolean isVegetarien,
                                        boolean isNonveg,
                                        boolean isSeasonal,
                                        String foodCategory
    );

    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long foodId)throws Exception;

    public Food updateAvailabilityStatus(Long foodId) throws Exception;

}
