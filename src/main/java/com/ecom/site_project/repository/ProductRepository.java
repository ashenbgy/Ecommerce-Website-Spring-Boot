package com.ecom.site_project.repository;

import com.ecom.site_project.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT product FROM Product product WHERE (product.category.id = ?1 OR product.category.allParentsIDs LIKE %?2%)"
            + "ORDER BY product.title ASC")
    Page<Product> listByCategory(Integer categoryId, Pageable pageable, String categoryIDMatch);

//    @Query(value = "SELECT * FROM product WHERE MATCH(title, description) AGAINST (?1)",
    @Query(value = "SELECT * FROM product WHERE title LIKE %?1% OR description LIKE %?1% ",
            nativeQuery = true)
    Page<Product> search(String keyword, Pageable pageable);

    Product findByAlias(String alias);

    Product findByTitle(String title);

    List<Product> findAllByCategoryId(Integer category);

    Long countById(Integer id);

    @Query("SELECT p FROM Product p WHERE p.title LIKE %?1% "
            + "OR p.description LIKE %?1% "
            + "OR p.vendor.title LIKE %?1% "
            + "OR p.category.title LIKE %?1%")
    Page<Product> findAll(String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = ?1 " +
            "OR p.category.allParentsIDs LIKE %?2%")
    Page<Product> findAllInCategory(Integer categoryId, String categoryIdMatch,
                                    Pageable pageable);

    @Query("SELECT p FROM Product p WHERE (p.category.id = ?1 " +
            "OR p.category.allParentsIDs LIKE %?2%) AND "
            + "(p.title LIKE %?3% "
            + "OR p.description LIKE %?3% "
            + "OR p.vendor.title LIKE %?3% "
            + "OR p.category.title LIKE %?3%)")
    Page<Product> searchInCategory(Integer categoryId, String categoryIdMatch,
                                   String keyword, Pageable pageable);
}
