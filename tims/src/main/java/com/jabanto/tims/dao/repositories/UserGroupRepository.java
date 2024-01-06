package com.jabanto.tims.dao.repositories;


import com.jabanto.tims.dao.models.User;
import com.jabanto.tims.dao.models.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserGroupRepository extends JpaRepository <UserGroup, Integer> {

    Optional<UserGroup> findByGroupName(String groupName);

}
