package com.ecom.site_project.repository;

import com.ecom.site_project.entity.Order;
import com.ecom.site_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findByUser(User user);

    List<Order> findOrdersByUser(User user);

    Long countById(Integer id);

}
