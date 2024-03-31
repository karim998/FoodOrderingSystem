package com.TalabatSystem.Services;

import com.TalabatSystem.Models.Cart;
import com.TalabatSystem.Models.CartItem;
import com.TalabatSystem.Requests.AddCartItemRequest;

public interface CartService {

    public CartItem addItemToCart(AddCartItemRequest request, String jwt)throws Exception;

    public CartItem updateCartItemQuantity(Long cartItemId, int quantity)throws Exception;

    public Cart removeItemFromCart(Long cartItemId, String jwt)throws Exception;

    public Long calculateCartTotals(Cart cart)throws Exception;

    public Cart findCartById(Long id)throws Exception;

    public Cart findCartByUserId(Long userId)throws Exception;

    public Cart cleanCart(Long userId)throws Exception;
}
