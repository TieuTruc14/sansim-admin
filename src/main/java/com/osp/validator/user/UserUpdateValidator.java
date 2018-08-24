package com.osp.validator.user;


import com.osp.common.Utils;
import com.osp.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Admin on 12/27/2017.
 */
@Component
public class UserUpdateValidator implements Validator {
    //which objects can be validated by this validator
    @Override
    public boolean supports(Class<?> paramClass) {
        return User.class.equals(paramClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Utils.trimAllFieldOfObject(obj);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "id.error");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "fullName.error");

    }
}
