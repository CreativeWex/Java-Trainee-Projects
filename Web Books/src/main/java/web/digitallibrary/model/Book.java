package web.digitallibrary.model;

/*
    =====================================
    @project DigitalLibrary
    @created 12/01/2023
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class Book {
    @NonNull
    private int id;

    @NonNull
    private int genreId;

    @NonNull
    private int authorId;

    @Size(min = 2, max = 100, message = "Имя должно быть не меньше 2 и не больше 100 символов!")
    private String name;

    @NonNull
    private String genre;

    @NonNull
    private String author;

    @NonNull
    private String status;

    @Min(value = 1, message = "Год должен быть больше 0")
    @Max(value = 2049, message = "Год не должен превышать 2049")
    private int year;

    private String description;

    public Book() {}

    public Book(int id, int genreId, int authorId, String name, @NonNull String genre, @NonNull String author, int year,
                String description, String status) {
        this.id = id;
        this.genreId = genreId;
        this.authorId = authorId;
        this.name = name;
        this.genre = genre;
        this.author = author;
        this.year = year;
        this.description = description;
        this.status = status;
    }
}
