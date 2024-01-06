package com.jabanto.tims.service.registration;
import com.jabanto.tims.configuration.StageInitializer;
import com.jabanto.tims.dao.models.ConfirmationToken;
import com.jabanto.tims.dao.models.User;
import com.jabanto.tims.dao.models.UserGroup;
import com.jabanto.tims.dao.models.UserRole;
import com.jabanto.tims.service.generic.ConfirmationTokenService;
import com.jabanto.tims.service.generic.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {


    private final static String USER_EMAIL_NOT_VALID = "Email format is not valid.";
    private final static String USER_TOKEN_NOT_FOUND = "Token not found";
    private final static String TOKEN_NOT_CONFIRMED = "User Token already confirmed";
    private final static String TOKEN_EXPIRED ="Token is expired";

    //We se Lombock allArgsContructor anotation to spara use autowired this to objects
    // This implementation is only use for in this class to test lombock
    private final EmailValidatorService emailValidatorService;
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;


    @Transactional
    public int register(User newUser){

        Boolean isValidEmail = emailValidatorService.test("Email");
        if (!isValidEmail){
            throw new IllegalStateException(USER_EMAIL_NOT_VALID);
        }
        return userService.signUpUser(newUser);
    }

    @Transactional
    public  String confirmToken(String token){

        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(()-> new IllegalStateException(USER_TOKEN_NOT_FOUND));

        if(confirmationToken.getConfirmAt() !=null){
            throw  new IllegalStateException(TOKEN_NOT_CONFIRMED);
        }

        LocalDateTime expireAt = confirmationToken.getExpiresAt();

        if(expireAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException(TOKEN_EXPIRED);
        }
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        userService.enableAppUser(confirmationToken.getUser().getEmail());

      return "User token confirmed";
    }


}
