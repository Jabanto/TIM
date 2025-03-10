package com.jabanto.tims.service.generic;
import com.jabanto.tims.dao.models.UserGroup;
import com.jabanto.tims.dao.repositories.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserGroupService {

    @Autowired
    private UserGroupRepository userGroupRepository;

    public List<UserGroup> readUserGroup(){ return userGroupRepository.findAll();}

    public List<String> getGroupNames(){

        List<UserGroup> roleList =  readUserGroup();
        List<String> nameGroup = roleList.stream()
                .map(UserGroup::getGroupName)
                .collect(Collectors.toList());
        return nameGroup;
    }

    public Optional<UserGroup> getGroup(String groupName) {
        return userGroupRepository.findByGroupName(groupName);
    }
}
