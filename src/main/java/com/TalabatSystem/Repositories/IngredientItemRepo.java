package com.TalabatSystem.Repositories;

import com.TalabatSystem.Models.IngrediantsItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientItemRepo extends JpaRepository<IngrediantsItem, Long> {
    List<IngrediantsItem> findByRestaurantId(Long id);
}
