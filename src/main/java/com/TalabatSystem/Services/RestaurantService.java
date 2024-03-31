package com.TalabatSystem.Services;

import com.TalabatSystem.Models.Restaurant;
import com.TalabatSystem.Models.User;
import com.TalabatSystem.Requests.CreateRestaurantRequest;
import com.TalabatSystem.dto.RestaurantDto;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurant();

    public List<Restaurant> searchRestaurant(String keyword);

    public Restaurant findRestaurantById(Long id) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addToFavorit(Long restaurantId, User user)throws Exception;

    public Restaurant updateRestaurantStatus(Long id)throws Exception;

}
