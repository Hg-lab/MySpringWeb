package com.kideveloper.my_spring_web.validator;

import com.kideveloper.my_spring_web.model.Board;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;


@Component
public class BoardValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Board.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Board b = (Board) obj;
        if(StringUtils.isEmpty(b.getContent())) {
            errors.rejectValue("content", "key", "Input the content.");
        }
    }
}
