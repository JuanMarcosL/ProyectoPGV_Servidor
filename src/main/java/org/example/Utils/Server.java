package org.example.Utils;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Server {

    private static double usedPercentage;
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
                                double ramUsage = monitorizeRAM();


                                recogerDatos.iniciarMonitorizacion();

                                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                                String mensajeFinal = recogerDatos.getMensaje().toString();
                                System.out.println("En server" + mensajeFinal);
                                outToClient.writeBytes(mensajeFinal + "\n");

                                //outToClient.writeBytes(String.valueOf(ramUsage) + "\n");
                            } else {
                                this.cancel(); // Cancela la tarea si la conexión está cerrada
                            }
                        } catch (IOException e) {
                            this.cancel(); // Cancela la tarea si ocurre una excepción
                            e.printStackTrace();
                        }
                    }
                };

                timer.schedule(task, 0, 1000);
            }).start();

        }
    }

    public static double monitorizeRAM() {
        SystemInfo si = new SystemInfo();
        GlobalMemory memory = si.getHardware().getMemory();
        long totalMemory = memory.getTotal();
        long availableMemory = memory.getAvailable();
        long usedMemory = totalMemory - availableMemory;
        usedPercentage = (double) usedMemory / totalMemory * 100;
        return usedPercentage;
    }
}