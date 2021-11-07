package com.audriuskumpis.labOneThird.myValidators;

import com.pirmauzduotis.PasswordChecker;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyPasswordValidator {

    private final PasswordChecker passwordChecker;

    private MyPasswordValidator() {
        this.passwordChecker = new PasswordChecker();
    }

    public boolean validate(String password) {
        return passwordChecker.check(password);
    }

    public void addSpecialSymbol(char specialChar) {
        passwordChecker.addSpecialSymbol(specialChar);
    }

    public void removeSpecialChar(char specialChar) {
        passwordChecker.removeSpecialSymbol(specialChar);
    }

    public void setSpecialSymbolList(List<Character> chars) {
        passwordChecker.setSpecialSymbolList(chars);
    }

    public List<Character> getSpecialChars() {
        return passwordChecker.getSpecialSymbolList();
    }

    public void setMinLength(int length) {
        passwordChecker.setMinLength(length);
    }

}
