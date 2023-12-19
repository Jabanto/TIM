package com.jabanto.tims.dao.repositories;

import com.jabanto.tims.dao.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends JpaRepository<Item,Integer> {

    @Query("select max(s.id) from item s")
    public Integer findMaxId();

}
