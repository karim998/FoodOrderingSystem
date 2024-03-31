package com.TalabatSystem.Controllers;

import com.TalabatSystem.Models.Food;
import com.TalabatSystem.Models.Restaurant;
import com.TalabatSystem.Models.User;
import com.TalabatSystem.Requests.CreateFoodRequest;
import com.TalabatSystem.Response.MessageResponse;
import com.TalabatSystem.Services.FoodService;
import com.TalabatSystem.Services.RestaurantService;
import com.TalabatSystem.Services.UserService;
import jdk.jfr.Frequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest request,
                                           @RequestHeader("Authorization") String jwt)throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());
        Food food = foodService.createFood(request, request.getCategory(),restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt)throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        foodService.deleteFood(id);

        MessageResponse response = new MessageResponse();
        response.setMessage("Food Delete Successfully");

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PutMapping("/{id")
    public ResponseEntity<Food> updateFoodAvailabilityStatus(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt)throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailabilityStatus(id);

        MessageResponse response = new MessageResponse();
        response.setMessage("Food Delete Successfully");

        return new ResponseEntity<>(food, HttpStatus.CREATED);

    }



}
