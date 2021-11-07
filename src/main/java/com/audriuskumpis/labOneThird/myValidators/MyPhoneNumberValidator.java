package com.audriuskumpis.labOneThird.myValidators;

import com.pirmauzduotis.PhoneValidator;
import org.springframework.stereotype.Component;

@Component
public class MyPhoneNumberValidator {

    private final PhoneValidator phoneValidator;

    private MyPhoneNumberValidator() {
        this.phoneValidator = new PhoneValidator();
    }

    public boolean validate(String number) {
        return phoneValidator.validate(number);
    }
}
