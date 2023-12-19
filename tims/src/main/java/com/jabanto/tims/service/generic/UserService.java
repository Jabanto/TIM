package com.jabanto.tims.service.generic;


import com.jabanto.tims.dao.models.User;
import com.jabanto.tims.dao.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> readUser(){ return userRepository.findAll();}

    public Optional<User> readUserById(int Id){ return userRepository.findById(Id);}

}
