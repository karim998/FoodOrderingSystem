package com.TalabatSystem.Repositories;

import com.TalabatSystem.Models.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepo extends JpaRepository<Food, Long> {

    List<Food> findByRestaurantId(long restaurantId);

    @Query("SELECT f From Food f WHERE f.name LIKE %:keyword% OR f.foodcategory.name LIKE %:keyword%")
    List<Food> searchFood(@Param("keyword") String keyword);
}
