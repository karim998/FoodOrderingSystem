package com.TalabatSystem.Services;

import com.TalabatSystem.Models.Cart;
import com.TalabatSystem.Models.CartItem;
import com.TalabatSystem.Models.Food;
import com.TalabatSystem.Models.User;
import com.TalabatSystem.Repositories.CartItemRepo;
import com.TalabatSystem.Repositories.CartRepo;
import com.TalabatSystem.Requests.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImp implements CartService{

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addItemToCart(AddCartItemRequest request, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(request.getFoodId());
        Cart cart = cartRepo.findByCustomerId(user.getId());

        for(CartItem cartItem : cart.getItems()){
            if(cartItem.getFood().equals(food)){
                int newQuantity = cartItem.getQuantity() + request.getQuantity();
                return updateCartItemQuantity(cartItem.getId(),newQuantity);
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(request.getQuantity());
        newCartItem.setIngredients(request.getIngredients());
        newCartItem.setTotalPrice(request.getQuantity() * food.getPrice());

        CartItem savedCartItem = cartItemRepo.save(newCartItem);

        cart.getItems().add(savedCartItem);

        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {

        //Find Cart Item
        Optional<CartItem> cartItemOptional = cartItemRepo.findById(cartItemId);
        if (cartItemOptional.isEmpty()){
            throw new Exception("Cart Item Not Found");
        }
        CartItem item = cartItemOptional.get();
        item.setQuantity(quantity);

        //total price 5*10 = 50
        item.setTotalPrice(item.getFood().getPrice());

        return cartItemRepo.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepo.findByCustomerId(user.getId());

        //Find Cart Item
        Optional<CartItem> cartItemOptional = cartItemRepo.findById(cartItemId);
        if (cartItemOptional.isEmpty()){
            throw new Exception("Cart Item Not Found");
        }

        CartItem item = cartItemOptional.get();
        cart.getItems().remove(item);

        return cartRepo.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {
        Long total = 0L;

        for(CartItem cartItem : cart.getItems()){
            total+=cartItem.getFood().getPrice()*cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> optionalCart = cartRepo.findById(id);
        if (optionalCart.isEmpty()){
            throw new Exception("Cart Not Found With Id " + id);
        }
        return optionalCart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
        //User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepo.findByCustomerId(userId);
        cart.setTotal(calculateCartTotals(cart));
        return cart;
    }

    @Override
    public Cart cleanCart(Long userId) throws Exception {
        //User user = userService.findUserByJwtToken(jwt);
        Cart cart = findCartByUserId(userId);

        cart.getItems().clear();
        return cartRepo.save(cart);
    }
}
