package com.TalabatSystem.Controllers;

import com.TalabatSystem.Models.Cart;
import com.TalabatSystem.Models.CartItem;
import com.TalabatSystem.Models.User;
import com.TalabatSystem.Requests.AddCartItemRequest;
import com.TalabatSystem.Requests.UpdateCartItemRequest;
import com.TalabatSystem.Services.CartService;
import com.TalabatSystem.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;


    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest request,
                                                  @RequestHeader("Authorization")String jwt) throws Exception {

        CartItem cartItem = cartService.addItemToCart(request, jwt);
        return new ResponseEntity<>(cartItem,HttpStatus.OK);

    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateCartItemRequest request,
                                                           @RequestHeader("Authorization")String jwt) throws Exception {

        CartItem cartItem = cartService.updateCartItemQuantity(request.getCartItemId(),request.getQuantity());
        return new ResponseEntity<>(cartItem,HttpStatus.OK);

    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeCartItem(
            @PathVariable Long id,
            @RequestHeader("Authorization")String jwt) throws Exception {

        Cart cart = cartService.removeItemFromCart(id, jwt);
        return new ResponseEntity<>(cart,HttpStatus.OK);

    }

    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> ClearCart(
            @RequestHeader("Authorization")String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findCartByUserId(user.getId());
        return new ResponseEntity<>(cart,HttpStatus.OK);

    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> FindUserCart(
            @RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findCartByUserId(user.getId());
        return new ResponseEntity<>(cart,HttpStatus.OK);

    }
}
