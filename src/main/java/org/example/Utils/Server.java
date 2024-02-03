package org.example.Utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Server {

    private static recogerDatos recogerDatos = new recogerDatos();

    public static void main(String[] args) throws IOException {
        int port = 6789;
        ServerSocket welcomeSocket = new ServerSocket(port);

        while (true) {

            Socket connectionSocket = welcomeSocket.accept();

            new Thread(() -> {
                Timer timer = new Timer();

                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        try {

                            if (!connectionSocket.isClosed()) {
                                recogerDatos.iniciarMonitorizacion();

                                // Esperar hasta que los datos estén disponibles
                                while (recogerDatos.getMensaje().length() == 0) {
                                    Thread.sleep(100);
                                }

                                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                                String mensajeFinal = recogerDatos.getMensaje().toString();
                                System.out.println("En server" + mensajeFinal);
                                outToClient.writeBytes(mensajeFinal + "\n");
                            } else {
                                this.cancel(); // Cancela la tarea si la conexión está cerrada
                            }
                        } catch (IOException | InterruptedException e) {
                            this.cancel(); // Cancela la tarea si ocurre una excepción
                            e.printStackTrace();
                        }
                    }
                };

                timer.schedule(task, 0, 1000);
            }).start();

        }
    }
}