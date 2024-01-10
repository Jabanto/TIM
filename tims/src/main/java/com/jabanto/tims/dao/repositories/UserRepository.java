package com.jabanto.tims.dao.repositories;


import com.jabanto.tims.dao.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE user a " +
            "SET a.enable = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);

    @Transactional
    @Query("SELECT u FROM user u WHERE u.userRole.id = ?1")
    List<User> findByUserRoleId(int roleId);

    @Transactional
    @Query("SELECT u FROM user u WHERE u.userRole.id = :roleId")
    List<User> findByUserRole(@Param("roleId") int roleId);
}
