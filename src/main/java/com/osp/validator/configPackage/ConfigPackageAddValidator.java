package com.osp.validator.configPackage;

import com.osp.common.Utils;
import com.osp.model.view.GroupView;
import com.osp.modelCustomer.ConfigPackage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Admin on 1/3/2018.
 */
@Component
public class ConfigPackageAddValidator implements Validator {
    @Override
    public boolean supports(Class<?> paramClass) {
        return ConfigPackage.class.equals(paramClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ConfigPackage item = (ConfigPackage) obj;
        Utils.trimAllFieldOfObject(item);
        if(StringUtils.isBlank(item.getPackageCode()) || item.getPackageCode().length()>50){
            errors.rejectValue("packageCode", "package.packageCode");
        }
        if(StringUtils.isBlank(item.getPackageName()) || item.getPackageName().length()>200){
            errors.rejectValue("packageCode", "package.packageName");
        }
        if(item.getMaxQuantity()==null || !(item.getMaxQuantity().intValue()>0) ){
            errors.rejectValue("maxQuantity","empty");
        }
        if(item.getFee()==null || !(item.getFee().longValue()>0) ){
            errors.rejectValue("fee","empty");
        }
        if(item.getPeriod()==null || !(item.getPeriod().intValue()>0) ){
            errors.rejectValue("period","empty");
        }

    }
}
