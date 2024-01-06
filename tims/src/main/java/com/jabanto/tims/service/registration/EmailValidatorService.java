package com.jabanto.tims.service.registration;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TableGenerator;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class EmailValidatorService implements Predicate<String> {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private Pattern patron = Pattern.compile(EMAIL_PATTERN);

    @Override
    public boolean test(String email) {
        Matcher matcher = patron.matcher(email);
        return matcher.matches();
    }

}
