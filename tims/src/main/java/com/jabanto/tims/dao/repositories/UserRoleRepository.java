package com.jabanto.tims.dao.repositories;


import com.jabanto.tims.dao.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {

    Optional<UserRole> findByRoleName(String rolename);
}
