package com.jabanto.tims.service.generic;


import com.jabanto.tims.dao.models.ConfirmationToken;
import com.jabanto.tims.dao.repositories.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ConfirmationTokenService {

    @Autowired
    ConfirmationTokenRepository tokenRepository;

    public void saveConfirmationToken(ConfirmationToken token){
        tokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token){
        return tokenRepository.findByToken(token);
    }
}
