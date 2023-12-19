package com.jabanto.tims.dao.repositories;


import com.jabanto.tims.dao.models.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends JpaRepository <UserGroup, Integer> {

}
