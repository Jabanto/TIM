package com.jabanto.tims.service.generic;

import com.jabanto.tims.dao.models.Category;
import com.jabanto.tims.dao.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> readCategories(){
        return categoryRepository.findAll();
    }

    public List<String> getCategoriesNames() {
            List<Category> roleList =  readCategories();
            List<String> categoriesNames = roleList.stream()
                    .map(Category::getName)
                    .collect(Collectors.toList());
            return categoriesNames;
    }

    public Optional<Category> getCategory(String categoryName) {
        return categoryRepository.findByName(categoryName);
    }
}
