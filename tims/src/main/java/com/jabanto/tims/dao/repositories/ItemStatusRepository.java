package com.jabanto.tims.dao.repositories;

import com.jabanto.tims.dao.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemStatusRepository extends JpaRepository<Status, Integer> {
    Optional<Status> findByName(String statusName);
}
