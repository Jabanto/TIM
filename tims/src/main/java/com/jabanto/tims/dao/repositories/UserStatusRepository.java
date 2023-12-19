package com.jabanto.tims.dao.repositories;

import com.jabanto.tims.dao.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatusRepository extends JpaRepository<Status, Integer> {
}
