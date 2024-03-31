package com.TalabatSystem.Services;

import com.TalabatSystem.Models.Address;
import com.TalabatSystem.Models.ContactInformation;
import com.TalabatSystem.Models.Restaurant;
import com.TalabatSystem.Models.User;
import com.TalabatSystem.Repositories.AddressRepo;
import com.TalabatSystem.Repositories.RestaurantRepo;
import com.TalabatSystem.Repositories.UserRepo;
import com.TalabatSystem.Requests.CreateRestaurantRequest;
import com.TalabatSystem.dto.RestaurantDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private UserRepo userRepo;
    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {

        Address address = addressRepo.save(req.getAddress());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegisterationDate(LocalDateTime.now());
        restaurant.setOwner(user);

        return restaurantRepo.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        if(restaurant.getCuisineType()!= null){
            restaurant.setCuisineType(updateRestaurant.getCuisineType());
        }
        if(restaurant.getDescription()!= null){
            restaurant.setDescription((updateRestaurant.getDescription()));
        }
        if(restaurant.getName()!= null){
            restaurant.setName(updateRestaurant.getName());
        }
        return restaurantRepo.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        restaurantRepo.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepo.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepo.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt = restaurantRepo.findById(id);

        if(opt.isEmpty()) {
            throw new Exception("Restaurant Not Found with id" + id);
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepo.findByOwnerId(userId);
        if (restaurant==null){
            throw new Exception("Restaurant Not Found With Owner Id" + userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavorit(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        RestaurantDto dto = new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurantId);

        boolean isFavourited = false;
        List<RestaurantDto> favourites = user.getFavourits();
        for(RestaurantDto favourite : favourites){
            if(favourite.getId().equals(restaurantId)){
                isFavourited = true;
                break;
            }
        }
        if(isFavourited){
            favourites.removeIf(favourite ->favourite.getId().equals(restaurantId));
        }
        else {
            favourites.add(dto);
        }
        userRepo.save(user);
        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepo.save(restaurant);
    }
}
