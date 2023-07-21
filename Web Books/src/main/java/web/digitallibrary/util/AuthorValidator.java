package web.digitallibrary.util;

/*
    =====================================
    @project DigitalLibrary
    @created 12/01/2023
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import web.digitallibrary.DAO.AuthorDAO;
import web.digitallibrary.model.Author;

@Component
public class AuthorValidator implements Validator {
    private final AuthorDAO authorDAO;

    @Autowired
    public AuthorValidator(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Author.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Author author = (Author) target;
        if(authorDAO.findSimilarName(author.getName(), author.getId()).isPresent()) {
            errors.rejectValue("name", "", "Автор с данным именем уже существует");
        }
    }
}
