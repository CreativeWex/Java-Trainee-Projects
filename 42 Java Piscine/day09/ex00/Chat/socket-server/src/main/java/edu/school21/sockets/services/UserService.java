package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserService {
    boolean signUp(User user);
}
