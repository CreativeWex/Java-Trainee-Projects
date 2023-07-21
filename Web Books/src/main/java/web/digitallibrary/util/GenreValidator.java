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
import web.digitallibrary.DAO.GenreDAO;
import web.digitallibrary.model.Genre;

@Component
public class GenreValidator implements Validator {
    private final GenreDAO genreDAO;

    @Autowired
    public GenreValidator(GenreDAO genreDAO) {
        this.genreDAO = genreDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Genre.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Genre genre = (Genre) target;
        if (genreDAO.getSimilarGenre(genre.getName(), genre.getId()).isPresent()) {
            errors.rejectValue("name", "", "Данный жанр уже существует");
        }
    }
}
