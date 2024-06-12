package com.ecom.site_project.service;

import com.ecom.site_project.entity.Product;
import com.ecom.site_project.exception.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    Page<Product> listByPage(int pageNum, String sortField, String sortDir, String keyword,
                             Integer categoryId);

    List<Product> getAllProducts() throws ProductNotFoundException;

    List<Product> getRandomAmountOfProducts() throws ProductNotFoundException;

    Page<Product> listByCategory(int pageNum, Integer categoryId);

    void saveProduct(Product product, MultipartFile file) throws IOException, ProductNotFoundException;

    Product getProduct(Integer id) throws ProductNotFoundException;

    Product getProduct(String alias) throws ProductNotFoundException;

    void deleteProduct(Integer id) throws ProductNotFoundException;

    String checkUnique(Integer id, String title);

    Page<Product> search(String keyword, int pageNum);
}
