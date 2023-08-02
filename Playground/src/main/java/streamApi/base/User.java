package streamApi.base;
/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
@Data
@AllArgsConstructor
@ToString
public class User {
    private long id;
    private String firstName;
    private String lastName;
    private int age;
    private String nationality;
}
