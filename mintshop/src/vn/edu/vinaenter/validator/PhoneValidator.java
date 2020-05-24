package vn.edu.vinaenter.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.edu.vinaenter.models.User;

public class PhoneValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(User.class);
    }

    @Override
    public void validate(Object obj, Errors error) {
        User objUser = (User) obj;
        if (!objUser.getPhone().matches("\\d{10}")) {
            error.reject("phone", "Số điện thoại phải đúng định dạng và gồm 10 chữ số");
        }
    }
}
