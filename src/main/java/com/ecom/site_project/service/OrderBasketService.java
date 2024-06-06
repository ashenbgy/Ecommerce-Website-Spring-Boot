package com.ecom.site_project.service;

import com.ecom.site_project.entity.OrderBasket;
import com.ecom.site_project.entity.User;

import java.util.List;

public interface OrderBasketService {
    List<OrderBasket> getAllOrderBaskets();

    List<OrderBasket> listOrderBasket(User user);

    Integer addProduct(Integer productId, Integer quantity, User user);

    float updateQuantity(Integer productId, Integer quantity, User user);

    void removeProduct(Integer productId, User user);
}
