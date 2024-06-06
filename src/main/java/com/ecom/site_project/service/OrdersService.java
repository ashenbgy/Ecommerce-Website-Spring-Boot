package com.ecom.site_project.service;

import com.ecom.site_project.entity.Order;
import com.ecom.site_project.entity.OrderBasket;
import com.ecom.site_project.entity.User;
import com.ecom.site_project.exception.OrderNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrdersService {
    List<Order> getAllOrders();

    List<Order> getAllOrdersByUser(User user);

    void saveOrder(Order orders);

    Order getOrder(int id);

    Order getOrderByUser(User user) throws OrderNotFoundException;

    float countSum(List<OrderBasket> orderBaskets);

    void deleteOrder(int id) throws OrderNotFoundException;


    Page<Order> listByPage(int pageNum);
}
