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
import web.digitallibrary.DAO.ClientDAO;
import web.digitallibrary.model.Client;

@Component
public class ClientValidator implements Validator {
    private final ClientDAO clientDAO;

    @Autowired
    public ClientValidator(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Client.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Client client = (Client) target;
        if (clientDAO.findSimilarName(client.getName(), client.getId()).isPresent()) {
            errors.rejectValue("name", "", "Пользователь с данным именем уже существует!");
        } else if (clientDAO.findSimilarEmail(client.getEmail(), client.getId()).isPresent()) {
            errors.rejectValue("email", "", "Пользователь с данным email уже существует!");
        } else if (clientDAO.findSimilarPhoneNumber(client.getPhoneNumber(), client.getId()).isPresent()) {
            errors.rejectValue("phoneNumber", "", "Пользователь с данным телефонным номером уже существует!");
        }
    }
}
