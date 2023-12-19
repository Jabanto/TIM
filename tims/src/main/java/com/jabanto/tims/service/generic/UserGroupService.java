package com.jabanto.tims.service.generic;


import com.jabanto.tims.dao.models.UserGroup;
import com.jabanto.tims.dao.repositories.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupService {

    @Autowired
    private UserGroupRepository userGroupRepository;

    public List<UserGroup> readUserGroup(){ return userGroupRepository.findAll();}

}
