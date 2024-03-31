package com.TalabatSystem.Services;

import com.TalabatSystem.Models.Order;
import com.TalabatSystem.Models.User;
import com.TalabatSystem.Requests.OrderRequest;
import org.springframework.boot.web.servlet.filter.OrderedRequestContextFilter;

import java.util.List;

public interface OrderService {

    public Order createOrder(OrderRequest order, User user) throws Exception;

    public Order updateOrder(Long orderId, String orderStatus) throws Exception;

    public void cancelOrder(Long orderId) throws Exception;

    public List<Order> getUsersOrder(Long userId) throws Exception;

    public List<Order> getRestaurantsOrder(Long restaurantId ,String orderStatus)throws Exception;

    public Order findOrderById (Long orderId) throws Exception;
}
