package org.example.RecogerDatos;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class velocidadInternet {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private String velocidadInternetStr;  // Variable para almacenar la información de velocidad de Internet
    private boolean velocidadRecogida = false;  //Para controlar si la información ya se recogió

    public void recogerYEnviarVelocidadInternet() {
        if (!velocidadRecogida) {
            try {
                // URL de prueba
                URL url = new URL("https://www.google.com");
                URLConnection connection = url.openConnection();
                long startTime = System.currentTimeMillis();

                // Lee datos de la conexión para medir la velocidad
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                long totalBytesRead = 0;

                try (InputStream inputStream = connection.getInputStream()) {
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        totalBytesRead += bytesRead;
                    }
                }
                long endTime = System.currentTimeMillis();
                // Calcula la velocidad en megabits por segundo (Mbps)
                long tiempoTotal = endTime - startTime;
                double velocidadMbps = (totalBytesRead * 8.0 / 1024.0 / 1024.0) / (tiempoTotal / 1000.0);

                // Construir el mensaje final
                velocidadInternetStr = String.format("%.2f", velocidadMbps).replace(',', '.');
                //System.out.println(velocidadInternetStr);
                System.out.flush();  // Forzar la liberación del búfer

                // Marcar que la información ha sido recogida
                velocidadRecogida = true;
            } catch (IOException e) {
                // Manejar el error y almacenar un mensaje en la variable
                velocidadInternetStr = "Error al medir la velocidad de Internet: " + e.getMessage();
                System.out.println(velocidadInternetStr);
            }
        }
    }

    public String obtenerVelocidadInternet() {
        return velocidadInternetStr;
    }
}
