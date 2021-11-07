package com.audriuskumpis.labOneThird.myValidators;

import com.pirmauzduotis.EmailValidator;
import org.springframework.stereotype.Component;

@Component
public class MyEmailValidator {

    private final EmailValidator emailValidator;

    private MyEmailValidator() {
        this.emailValidator = new EmailValidator();
    }

    public boolean validate(String email) {
        return emailValidator.validate(email);
    }
}
