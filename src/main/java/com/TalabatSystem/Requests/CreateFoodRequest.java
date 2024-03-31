package com.TalabatSystem.Requests;

import com.TalabatSystem.Models.Category;
import com.TalabatSystem.Models.IngrediantsItem;
import com.TalabatSystem.Services.FoodService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
public class CreateFoodRequest {

    private String name;
    private String description;
    private Long price;

    private Category category;
    private List<String> images;

    private Long restaurantId;
    private boolean vegitarien;
    private boolean seasional;
    private List<IngrediantsItem> ingrediants;
}
