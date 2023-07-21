package web.digitallibrary.model;
/*
    =====================================
    @project DigitalLibrary
    @created 21/01/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class Order {
    @NonNull
    private int id;

    @NonNull
    private int clientId;

    @NonNull
    private int bookId;

    private String clientName;
    private String bookName;

    public Order() {}

    public Order(int id, int clientId, int bookId, String clientName, String bookName) {
        this.id = id;
        this.clientId = clientId;
        this.bookId = bookId;
        this.clientName = clientName;
        this.bookName = bookName;
    }
}
