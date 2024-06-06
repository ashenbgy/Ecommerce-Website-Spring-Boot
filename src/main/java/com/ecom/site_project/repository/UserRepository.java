package com.ecom.site_project.repository;

import com.ecom.site_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.login = :login")
    User findByLogin(@Param("login") String login);

    Long countById(Integer id);


}
