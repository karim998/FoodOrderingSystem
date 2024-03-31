package com.TalabatSystem.Services;

import com.TalabatSystem.Models.IngrediantCategory;
import com.TalabatSystem.Models.IngrediantsItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IngredientsService {

    public IngrediantCategory createIngredientsCategory(String name, Long restaurantId)throws Exception;

    public IngrediantCategory findIngredientsCategoryById(Long id) throws Exception;

    public List<IngrediantCategory> findIngredientsCategoryByRestaurantId(long id) throws Exception;

    public IngrediantsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception;

    public List<IngrediantsItem> findRestaurantIngredients(Long restaurantId);

    public IngrediantsItem updateStock(Long id) throws Exception;
}
