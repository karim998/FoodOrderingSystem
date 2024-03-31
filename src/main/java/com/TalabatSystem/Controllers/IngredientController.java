package com.TalabatSystem.Controllers;

import com.TalabatSystem.Models.IngrediantCategory;
import com.TalabatSystem.Models.IngrediantsItem;
import com.TalabatSystem.Requests.IngredientCategoryRequest;
import com.TalabatSystem.Requests.IngredientRequest;
import com.TalabatSystem.Services.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    @Autowired
    private IngredientsService ingredientsService;

    @PostMapping("/category")
    public ResponseEntity<IngrediantCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest request
            )throws Exception{
        IngrediantCategory item = ingredientsService.createIngredientsCategory(request.getName(), request.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<IngrediantsItem> createIngredientItem(
            @RequestBody IngredientRequest request
    )throws Exception{
        IngrediantsItem item = ingredientsService.createIngredientItem(request.getRestaurantId(),
                request.getName(),
                request.getCategoryId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<IngrediantsItem> updateIngredientStock(
            @PathVariable Long id
    )throws Exception{
        IngrediantsItem item = ingredientsService.updateStock(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngrediantsItem>> getRestaurantIngredient(
            @PathVariable Long id
    )throws Exception{
        List<IngrediantsItem> item = ingredientsService.findRestaurantIngredients(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/restaurnt/{id}/category")
    public ResponseEntity<List<IngrediantCategory>> getRestaurantIngredientCategory(
            @PathVariable Long id
    )throws Exception{
        List<IngrediantCategory> items = ingredientsService.findIngredientsCategoryByRestaurantId(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
