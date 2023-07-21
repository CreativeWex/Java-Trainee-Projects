package web.digitallibrary.model;

/*
    =====================================
    @project DigitalLibrary
    @created 12/01/2023
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class Author {
    @NonNull
    private int id;

    @Size(min = 2, max = 100, message = "Имя должно быть не меньше 2 и не больше 100 символов!")
    @Pattern(regexp = "([А-Я]\\. [А-Я]\\. [А-я][а-я]+)|([А-Я][а-я]+\\. [А-Я]\\. [А-я][а-я]+)|([А-Я]\\. [А-Я][а-я]+\\. " +
            "[А-я][а-я]+)|([А-Я][а-я]+ [А-Я][а-я]+ [А-я][а-я]+)|([А-Я][а-я]+ [А-Я][а-я]+)",
            message = "Неверный формат имени! Пример: А. П. Чехов, Джордж Оруэлл, Луиза Мэй Олкотт")
    private String name;

    @Pattern(regexp = "(1\\d\\d\\d)|([1-9]\\d)|([1-9]\\d\\d)|(\\d+ до н.э.)", message = "Введите год корректно! Пример: " +
            "1945, 234, 12 до н.э.")
    String dateOfBirth;

    @Pattern(regexp = "(1\\d\\d\\d)|([1-9]\\d)|([1-9]\\d\\d)|(\\d+ до н.э.)|()", message = "Введите год корректно или " +
            "Пример: 1945, 234, 12 до н.э.")
    String dateOfDeath;

    private String description;

    public Author() {}

    public Author(int id, String name, String dateOfBirth, String dateOfDeath, String description) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.description = description;
    }
}

