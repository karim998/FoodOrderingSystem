package com.TalabatSystem.Repositories;

import com.TalabatSystem.Models.IngrediantCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientCategoryRepo extends JpaRepository<IngrediantCategory,Long> {
    List<IngrediantCategory> findByRestaurantId(Long id);
}
