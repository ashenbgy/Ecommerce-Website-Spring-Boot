package com.ecom.site_project.service;

import com.ecom.site_project.entity.Category;
import com.ecom.site_project.exception.CategoryNotFoundException;
import com.ecom.site_project.service.impl.CategoryPageInfoServiceImpl;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    List<Category> listByPage(CategoryPageInfoServiceImpl pageInfo, int pageNum);

    List<Category> listCategoriesUserInForm();

    Category saveCategory(Category category, MultipartFile file) throws IOException, CategoryNotFoundException;

    void deleteCategory(int id) throws CategoryNotFoundException;

    Category getCategory(Integer id) throws CategoryNotFoundException;

    Category getCategoryByAlias(String alias) throws CategoryNotFoundException;

    //list up parent of categories
    List<Category> getCategoryParents(Category child);

    String checkCategoryTitle(Integer id, String title, String alias);
}
