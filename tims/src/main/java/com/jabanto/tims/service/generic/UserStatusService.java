package com.jabanto.tims.service.generic;

import com.jabanto.tims.dao.models.Status;
import com.jabanto.tims.dao.models.User;
import com.jabanto.tims.dao.repositories.UserStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStatusService {

    @Autowired
    UserStatusRepository userStatusRepository;

    public List<Status> readAllUserStatus(){ return userStatusRepository.findAll();}

}
