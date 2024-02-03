//package org.example;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//public class Client {
//    public static void main(String[] args) throws IOException {
//        Scanner scanner = new Scanner(System.in);
//        List<Socket> sockets = new ArrayList<>();
//
//        while (true) {
//            System.out.print("Introduce la dirección IP del servidor (o 'exit' para terminar): ");
//            String host = scanner.nextLine();
//            if ("exit".equalsIgnoreCase(host)) {
//                break;
//            }
//
//            System.out.print("Introduce el puerto del servidor: ");
//            int port = scanner.nextInt();
//            scanner.nextLine(); // consume the newline left-over
//
//            Socket socket = new Socket(host, port);
//            sockets.add(socket);
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//            new Thread(() -> {
//                try {
//                    while (true) {
//                        String message = in.readLine();
//                        System.out.println("Mensaje recibido del servidor: " + message);
//                    }
//                } catch (IOException e) {
//                    System.out.println("El servidor se ha desconectado.");
//                    try {
//                        socket.close();
//                    } catch (IOException ex) {
//                        ex.printStackTrace();
//                    }
//                    sockets.remove(socket);
//                }
//            }).start();
//        }
//
//        scanner.close();
//    }
//}
//

//package org.example;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Client {
//    private JTextField ipField;
//    private JTextField portField;
//    private JButton connectButton;
//    private List<Socket> sockets = new ArrayList<>();
//
//    public Client() {
//        JFrame frame = new JFrame("Cliente");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(300, 150);
//        frame.setLayout(new GridLayout(3, 2));
//
//        frame.add(new JLabel("Dirección IP:"));
//        ipField = new JTextField();
//        frame.add(ipField);
//
//        frame.add(new JLabel("Puerto:"));
//        portField = new JTextField();
//        frame.add(portField);
//
//        connectButton = new JButton("Conectar");
//        connectButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String host = ipField.getText();
//                int port = Integer.parseInt(portField.getText());
//
//                try {
//                    Socket socket = new Socket(host, port);
//                    sockets.add(socket);
//
//                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//                    new Thread(() -> {
//                        try {
//                            while (true) {
//                                String message = in.readLine();
//                                System.out.println("Mensaje recibido del servidor: " + message);
//                            }
//                        } catch (IOException ex) {
//                            System.out.println("El servidor se ha desconectado.");
//                            try {
//                                socket.close();
//                            } catch (IOException ex1) {
//                                ex1.printStackTrace();
//                            }
//                            sockets.remove(socket);
//                        }
//                    }).start();
//                } catch (NumberFormatException | IOException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });
//        frame.add(connectButton);
//
//        frame.setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        new Client();
//    }
//}

//


package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Client {
    private List<Socket> sockets = new ArrayList<>();
    private Map<String, String> servers = new HashMap<>();

    public Client() {
        startClient();
    }

    private void startClient() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Introduce el alias del servidor (o 'exit' para terminar): ");
            String alias = scanner.nextLine();
            if ("exit".equalsIgnoreCase(alias)) {
                break;
            }

            System.out.print("Introduce la dirección IP del servidor: ");
            String host = scanner.nextLine();

            System.out.print("Introduce el puerto del servidor: ");
            int port = scanner.nextInt();
            scanner.nextLine(); // consume the newline left-over

            connectToServer(alias, host, port);
        }

        scanner.close();
    }

    private void connectToServer(String alias, String host, int port) {
        String key = host + ":" + port;
        if (servers.containsKey(key)) {
            System.out.println("Esta dirección IP y puerto ya están en uso.");
            return;
        }

        try {
            Socket socket = new Socket(host, port);
            servers.put(key, alias);
            sockets.add(socket);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(() -> {
                try {
                    while (true) {
                        String message = in.readLine();
                        System.out.println("Mensaje recibido del servidor: " + message);
                    }
                } catch (IOException ex) {
                    System.out.println("El servidor se ha desconectado.");
                    try {
                        socket.close();
                    } catch (IOException ex1) {
                        ex1.printStackTrace();
                    }
                    sockets.remove(socket);
                    servers.remove(key);
                }
            }).start();

            System.out.println(alias + ": " + host + ":" + port);
        } catch (IOException ex) {
            System.out.println("No se ha podido conectar al servidor.");
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}

