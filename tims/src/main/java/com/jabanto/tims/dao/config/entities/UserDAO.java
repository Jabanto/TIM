package com.jabanto.tims.dao.config.entities;

import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

    public int login(String user, String password){

        int loginState = -1;

        if (user.equals("hola") && password.equals("alma")){

            loginState = 1;
        }

        return  loginState;

    }

}
