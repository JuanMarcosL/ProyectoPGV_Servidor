package org.example.RecogerDatos;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Clase que se encarga de medir la velocidad de Internet.
 */
public class VelocidadInternet {

    /**
     * Método que obtiene la velocidad de Internet.
     * @return La velocidad de Internet en megabits por segundo (Mbps) como un String.
     */
    public String obtenerVelocidadInternet() {
        String velocidadInternetStr;
        try {
            // URL de prueba para medir la velocidad de Internet
            URL url = new URL("https://www.apple.com");
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
            System.out.flush();  // Forzar la liberación del búfer
        } catch (IOException e) {
            // Manejar el error y almacenar un mensaje en la variable
            velocidadInternetStr = "Error al medir la velocidad de Internet: " + e.getMessage();
            System.out.println(velocidadInternetStr);
        }
        return velocidadInternetStr;
    }
}
