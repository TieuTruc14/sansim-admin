package com.osp.validator.category;

import com.osp.common.Utils;
import com.osp.modelCustomer.GroupYear;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Admin on 2/6/2018.
 */
@Component
public class GroupYearValidator implements Validator {
    @Override
    public boolean supports(Class<?> paramClass) {
        return GroupYear.class.equals(paramClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Utils.trimAllFieldOfObject(obj);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "year", "empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.error");
    }
}
