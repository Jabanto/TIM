package com.jabanto.tims.service.generic;

import com.jabanto.tims.dao.models.Category;
import com.jabanto.tims.dao.models.Item;
import com.jabanto.tims.dao.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> readItems(){
        return categoryRepository.findAll();
    }

}
