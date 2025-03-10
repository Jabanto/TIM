package com.jabanto.tims.service.generic;


import com.jabanto.tims.configuration.PasswordEncoder;
import com.jabanto.tims.dao.models.ConfirmationToken;
import com.jabanto.tims.dao.models.User;
import com.jabanto.tims.dao.repositories.UserRepository;
import com.jabanto.tims.service.registration.EmailValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private EmailValidatorService emailValidatorService;

    private final static String USER_EMAIL_NOT_FOUND = "User with email: %s not found.";
    public final static String USER_EMAIL_EXITS = "User with email already exits.";
    public final static String USER_EMAIL_INVALID = "User with email format not valid.";
    public final static String USER_CREATED = "New user created.";
    public final static String USER_DATABASE_ERROR = "Error creating new user.";

    private static final int COORDINATOR_ROLE_ID = 1;

    private static final int USER_UPDATE_SUCCESS = 1;
    public static final int USER_CREATED_SUCCESS = 1;
    public static final int USER_ALREADY_EXISTS = 2;
    public static final int USER_INVALID_EMAIL = 3;
    public static final int DATABASE_ERROR = 4;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserGroupService userGroupService;

    public List<User> readAllUsers(){ return userRepository.findAll();}

    public Optional<User> readUserById(int Id){ return userRepository.findById(Id);}

    public List<User> loadUsersByRole(int roleId){
        return userRepository.findByUserRoleId(roleId);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_EMAIL_NOT_FOUND,email)));
    }

    public int loginUser(String userName,String enteredPassword){
        int state = -1;
        Optional<User> user = userRepository.findByEmail(userName);
        boolean userExits = user.isPresent();
        String encodedPassword = user.get().getPassword();

        if(passwordEncoder.bCryptPasswordEncoder().matches(enteredPassword,encodedPassword)&&userExits){
            state = 1;
        }
        return state;
    }

    public boolean userEmailExits(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public Optional<User> loadUserByEmail(String email){ return userRepository.findByEmail(email); }

    public int signUpUser(User user){
        try{

            String encodedPassword = passwordEncoder.bCryptPasswordEncoder().encode(user.getPassword());
            user.setPassword(encodedPassword);
            boolean validEmail = emailValidatorService.test(user.getEmail());

            if(!validEmail){ return USER_INVALID_EMAIL; }

            userRepository.save(user);
            String token =  UUID.randomUUID().toString();
            ConfirmationToken confirmationToken = new ConfirmationToken(user,token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15));
            confirmationTokenService.saveConfirmationToken(confirmationToken);
            return USER_CREATED_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return DATABASE_ERROR;
        }
    }

    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }

    public int updateUser(User updateUser, User existingUser) {

        try {
            existingUser.setFirstName(updateUser.getFirstName());
            existingUser.setLastName(updateUser.getLastName());
            existingUser.setEmail(updateUser.getEmail());
            existingUser.setUserRole(updateUser.getUserRole());
            existingUser.setUserGroup(updateUser.getUserGroup());
            if (!updateUser.getPassword().isEmpty()){
                String encodedPassword = passwordEncoder.bCryptPasswordEncoder().encode(updateUser.getPassword());
                existingUser.setPassword(encodedPassword);
            }
            existingUser.setEnable(updateUser.getEnable());
            userRepository.save(existingUser);
            return USER_UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return  DATABASE_ERROR;
        }

    }

    public List<String> getReceiverNames() {

        List<User> receiverUsers = userRepository.findByUserRoleId(COORDINATOR_ROLE_ID);
        List<String> receivers = receiverUsers.stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
        return receivers;
    }

    public List<String> getGiverNames() {

        List<User> receiverUsers = userRepository.findByUserRole(1);
        List<String> receivers = receiverUsers.stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
        return receivers;
    }
}
