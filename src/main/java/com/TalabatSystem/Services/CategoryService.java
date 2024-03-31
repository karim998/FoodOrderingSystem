package com.TalabatSystem.Services;

import com.TalabatSystem.Models.Category;

import java.util.List;

public interface CategoryService {
    public Category createCategory(String name, Long userId) throws Exception;

    public List<Category> findCategoryByRestaurantId(Long id) throws Exception;

    public Category findCategoryById(long id)throws Exception;
}
