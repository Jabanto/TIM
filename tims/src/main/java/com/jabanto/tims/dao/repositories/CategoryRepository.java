package com.jabanto.tims.dao.repositories;


import com.jabanto.tims.dao.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository <Category,Integer> {
}
