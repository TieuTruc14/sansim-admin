package com.osp.validator.content;

import com.osp.common.Utils;
import com.osp.modelCustomer.Article;
import com.osp.modelCustomer.Category;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Admin on 2/28/2018.
 */
@Component
public class ArticleAddValidator implements Validator {
    @Override
    public boolean supports(Class<?> paramClass) {
        return Article.class.equals(paramClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Utils.trimAllFieldOfObject(obj);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "shortContent", "empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category.id", "empty");

    }
}
