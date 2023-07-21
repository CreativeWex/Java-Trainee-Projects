package edu.school21.sockets.client;

import java.io.*;
import java.net.Socket;

public class Client {

    private static Socket clientSocket; //клиентский сокет
    private static BufferedReader reader;
    private static BufferedReader in; // поток чтения из сокета
    private PrintWriter out; // отправка сообщений с автоматическим выталкиванием
    public Client(Integer port) {
        try {
            clientSocket = new Socket("localhost", port); // запрос на соединение по указанному порту
            reader = new BufferedReader(new InputStreamReader(System.in)); //
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void start(){
        try {
            String serverMsg = in.readLine();
            System.out.println(serverMsg);
            String signUpCommand = reader.readLine(); // читаем на стороне клиента
            out.println(signUpCommand); //отправляем серверу
            String serverAnswer = in.readLine();
            while(!("Enter username:").equals(serverAnswer)) {
                System.out.println(serverAnswer);
                signUpCommand = reader.readLine();
                out.println(signUpCommand);
                serverAnswer = in.readLine();
            }
            System.out.println(serverAnswer);
            String userName = reader.readLine();
            out.println(userName);
            serverAnswer = in.readLine();
            System.out.println(serverAnswer);
            String password = reader.readLine();
            out.println(password);
            serverAnswer = in.readLine();
            System.out.println(serverAnswer);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
