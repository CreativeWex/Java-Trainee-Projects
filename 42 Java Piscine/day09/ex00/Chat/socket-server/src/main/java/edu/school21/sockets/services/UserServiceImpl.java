package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {
    private UsersRepository usersRepository;
    private PasswordEncoder encoder;

    @Autowired
    @Qualifier("usersRepositoryJdbc")
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Autowired
    @Qualifier("encoder")
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;

    }

    @Override
    public boolean signUp(User user) {
        Optional<User> user1 = usersRepository.findByName(user.getName());
        if(user1.isPresent()) {
            return false;
        }
        user.setPassword(encoder.encode(user.getPassword()));
        usersRepository.save(user);
        return true;
    }
}
