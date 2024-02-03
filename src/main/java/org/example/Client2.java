package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client2 {
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 6789;
        Socket socket = new Socket(host, port);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        while (true) {
            String message = in.readLine();
            System.out.println("Mensaje recibido del Ayoze: " + message);
        }
    }
}
