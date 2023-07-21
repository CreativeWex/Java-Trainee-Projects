package edu.school21.sockets.server;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.services.UserService;
import edu.school21.sockets.services.UserServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Socket clientSocket;
    private ServerSocket server;
    private BufferedReader in;
    private PrintWriter out;
    UsersRepository usersRepository;
    UserService userService;

    public Server(Integer port) {
        try{
            server = new ServerSocket(port);
            clientSocket = server.accept();
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
            usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
            userService = context.getBean(UserService.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            out.println("Hello from server!");
            String regCommand = in.readLine();
            if(!("signUp".equalsIgnoreCase(regCommand))) {
                out.println("Please enter what do you want to do");
                regCommand = in.readLine();
            }
            out.println("Enter username:");
            String userName = in.readLine();
            out.println("Enter password:");
            String password = in.readLine();
            User user = new User(userName, password);
            if(userService.signUp(user)) {
                out.println("Successful!");
            } else {
                out.println("User with this name present in the table");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
