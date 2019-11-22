package com.lzy.mtnj.infrastructure.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class ValidatorUtil {
   public static String getAllError(BindingResult result) {
        boolean status = false;
        StringBuilder errors = new StringBuilder();

        for (ObjectError error :
                result.getAllErrors()) {
            if (!status) {
                status = true;
            } else {
                errors.append(";");
            }
            errors.append(error.getDefaultMessage());
        }

        return errors.toString();
    }
}
