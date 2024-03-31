package com.TalabatSystem.Services;

import com.TalabatSystem.Models.*;
import com.TalabatSystem.Repositories.*;
import com.TalabatSystem.Requests.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;
    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {
        Address shipAddress = order.getDeliveryAddress();
        Address savedAddress = addressRepo.save(shipAddress);

        if(!user.getAddresses().contains(savedAddress)){
            user.getAddresses().add(savedAddress);
            userRepo.save(user);
        }

        Restaurant restaurant = restaurantService.findRestaurantById(order.getRestaurantId());

        Order createdOrder = new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setRestaurant(restaurant);

        Cart cart = cartService.findCartById(user.getId());

        List<Orderitem> orderitems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()){
            Orderitem orderitem = new Orderitem();
            orderitem.setFood(cartItem.getFood());
            orderitem.setIngredients(cartItem.getIngredients());

            orderitem.setQuantity(cartItem.getQuantity());
            orderitem.setTotalPrice(cartItem.getTotalPrice());

            Orderitem savedOrderItem = orderItemRepo.save(orderitem);
            orderitems.add(savedOrderItem);

        }
        Long totalPrice = cartService.calculateCartTotals(cart);

        createdOrder.setItems(orderitems);
        createdOrder.setTotalPrice(totalPrice);

        Order savedOrder = orderRepo.save(createdOrder);
        restaurant.getOrders().add(savedOrder);

        return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);
        if (orderStatus.equals("Out_For_Delivery")
                || orderStatus.equals("Dilivered")
                || orderStatus.equals("Copleted")
                || orderStatus.equals("Pending")
        ){
            order.setOrderStatus(orderStatus);
            return orderRepo.save(order);
        }
        throw new Exception("Please select a Valid Order status");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order = findOrderById(orderId);
        orderRepo.deleteById(orderId);

    }

    @Override
    public List<Order> getUsersOrder(Long userId) throws Exception {

        return orderRepo.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {

        List<Order> orders = orderRepo.findByRestaurantId(restaurantId);
        if (orderStatus!=null){
            orders=orders.stream().filter(order ->
                    order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }
        return  orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> optionalOrder = orderRepo.findById(orderId);
        if(optionalOrder.isEmpty()){
            throw new Exception("Order Not Found");
        }
        return optionalOrder.get();
    }
}
