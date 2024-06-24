package com.ecom.site_project.service.impl;

import com.ecom.site_project.entity.Product;
import com.ecom.site_project.exception.ProductNotFoundException;
import com.ecom.site_project.repository.CategoryRepository;
import com.ecom.site_project.repository.ProductRepository;
import com.ecom.site_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    public static final int PRODUCTS_PER_PAGE = 10;
    public static final int SEARCH_RESULTS_PAGE = 10;
    public static final int PRODUCTS_PER_ADMIN_PAGE = 5;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Product> listByCategory(int pageNum, Integer categoryId) {
        String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);

        return productRepository.listByCategory(categoryId, pageable, categoryIdMatch);
    }

    @Override
    public Page<Product> listByPage(int pageNum, String sortField, String sortDir, String keyword,
                                    Integer categoryId) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_ADMIN_PAGE, sort);

        if (keyword != null && !keyword.isEmpty()) {
            if (categoryId != null && categoryId > 0) {
                String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
                return productRepository.searchInCategory(categoryId, categoryIdMatch, keyword, pageable);
            }
            return productRepository.findAll(keyword, pageable);
        }
        if (categoryId != null && categoryId > 0) {
            String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
            return productRepository.findAllInCategory(categoryId, categoryIdMatch, pageable);
        }

        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> getAllProducts() throws ProductNotFoundException {
        List<Product> listProducts = productRepository.findAll();
        if (listProducts.isEmpty()) {
            throw new ProductNotFoundException("Couldn't find any product!");
        }
        return listProducts;
    }

    @Override
    public List<Product> getRandomAmountOfProducts() throws ProductNotFoundException {
        int randomSeriesLength = 3;
        List<Product> productList = new ArrayList<>();

        List<Integer> catIdList = categoryRepository.findAllEnabledCategoryId();

        Collections.shuffle(catIdList);
        for (Integer categoryId : catIdList) {
            productList = productRepository.findAllByCategoryId(categoryId);
            if (!productList.isEmpty()) {
                break; // Exit loop if a non-empty product list is found
            }
        }

        if (!productList.isEmpty()) {
            Collections.shuffle(productList);
            if (randomSeriesLength > productList.size()) {
                randomSeriesLength = productList.size();
            }
        } else {
            throw new ProductNotFoundException("No products found!");
        }

        return productList.subList(0, randomSeriesLength);
    }

    @Override
    public void saveProduct(Product product, MultipartFile file) throws IOException, ProductNotFoundException {
        if (product.getAlias() == null || product.getAlias().isEmpty()) {
            String defaultAlias = product.getTitle().toLowerCase();
            product.setAlias((new CategoryServiceImpl().convertCyrillic(defaultAlias).replaceAll(" ", "_")));
        } else {
            product.setAlias(product.getAlias().replaceAll(" ", "_").toLowerCase());
        }
        if (product.getId() != null) {
            if (file.getSize() > 0) {
                product.setImageFile(Base64.getEncoder().encodeToString(file.getBytes()));
            } else {
                // If no new file is uploaded, retain the existing image
                Product existingProduct = getProduct(product.getId());
                product.setImageFile(existingProduct.getImageFile());
            }
        } else {
            // New product
            if (file.getSize() > 0) {
                // Image uploaded
                product.setImageFile(Base64.getEncoder().encodeToString(file.getBytes()));
            }
        }
        productRepository.save(product);
    }

    @Override
    public Product getProduct(Integer id) throws ProductNotFoundException {
        try {
            return productRepository.getReferenceById(id);
        } catch (NoSuchElementException e) {
            throw new ProductNotFoundException("Couldn't find any product with ID " + id);
        }
    }

    @Override
    public Product getProduct(String alias) throws ProductNotFoundException {
        try {
            return productRepository.findByAlias(alias);
        } catch (NoSuchElementException e) {
            throw new ProductNotFoundException("Couldn't find any product with alias " + alias);
        }
    }

    @Override
    public void deleteProduct(Integer id) throws ProductNotFoundException {
        Long countById = productRepository.countById(id);
        if (countById == null || countById == 0) {
            throw new ProductNotFoundException("Couldn't find any product with ID " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public String checkUnique(Integer id, String title) {
        boolean isCreatingNew = (id == null || id == 0);
        Product productByName = productRepository.findByTitle(title);
        if (isCreatingNew) {
            if (productByName != null) return "Duplicate";
        } else {
            if (productByName != null && !Objects.equals(productByName.getId(), id)) {
                return "Duplicate";
            }
        }
        return "OK";
    }

    @Override
    public Page<Product> search(String keyword, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, SEARCH_RESULTS_PAGE);
        return productRepository.search(keyword, pageable);

    }

    @Override
    public int getAvailableInventory(int productId) {
        return productRepository.findAvailableItemsByProductId(productId);
    }
}
