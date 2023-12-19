package com.jabanto.tims.service.generic;

import com.jabanto.tims.dao.models.UserRole;
import com.jabanto.tims.dao.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<UserRole> redUserRoles(){ return  userRoleRepository.findAll();}

}
