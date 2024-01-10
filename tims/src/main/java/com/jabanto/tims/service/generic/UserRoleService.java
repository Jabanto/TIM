package com.jabanto.tims.service.generic;

import com.jabanto.tims.dao.models.UserRole;
import com.jabanto.tims.dao.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<UserRole> readUserRoles(){ return  userRoleRepository.findAll();}

    public List<String> getRolesNames() {
        List<UserRole> roleList =  readUserRoles();
        List<String> nameRoles = roleList.stream()
                .map(UserRole::getRoleName)
                .collect(Collectors.toList());
        return nameRoles;
    }

    public Optional<UserRole> getRole(String rolename) {
        return userRoleRepository.findByRoleName(rolename);
    }
}
