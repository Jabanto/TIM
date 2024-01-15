package com.jabanto.tims.dao.repositories;

import com.jabanto.tims.dao.models.Item;
import com.jabanto.tims.dao.models.ItemType;
import com.jabanto.tims.dao.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemsRepository extends JpaRepository<Item,Integer> {

    @Query("select max(s.id) from item s")
    public Integer findMaxId();

    // Método que verifica si un nombre de item ya existe en la base de datos
    boolean existsByItemName(String itemName);

    <Optional> Item findByItemName(String itemName);

    @Transactional
    @Query("SELECT i FROM item i WHERE i.itemType = ?1")
    List<Item> findByItemGroup(ItemType groupName);

}
