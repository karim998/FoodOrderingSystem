package com.TalabatSystem.Controllers;

import com.TalabatSystem.Models.CartItem;
import com.TalabatSystem.Models.Order;
import com.TalabatSystem.Models.User;
import com.TalabatSystem.Requests.AddCartItemRequest;
import com.TalabatSystem.Requests.OrderRequest;
import com.TalabatSystem.Services.OrderService;
import com.TalabatSystem.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/orders")
    public ResponseEntity<Order> CreateOrder(@RequestBody OrderRequest request,
                                                  @RequestHeader("Authorization")String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(request,user);
        return new ResponseEntity<>(order, HttpStatus.OK);

    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(
                                             @RequestHeader("Authorization")String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        List<Order> order = orderService.getUsersOrder(user.getId());
        return new ResponseEntity<>(order, HttpStatus.OK);

    }
}
