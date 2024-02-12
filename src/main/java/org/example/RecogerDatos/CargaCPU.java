package org.example.RecogerDatos;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

/**
 * Clase que se encarga de obtener la carga de la CPU.
 */
public class CargaCPU {

    /**
     * Método que obtiene la carga actual de la CPU.
     * @return La carga de la CPU como un String en porcentaje.
     */
    public String obtenerCargaCPU() {
        // Recopila información de la CPU
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        CentralProcessor processor = hardware.getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();

        // Espera un segundo antes de calcular la carga de la CPU
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Calcula la carga de la CPU
        double cpuUsage = 5.0 + (processor.getSystemCpuLoadBetweenTicks(prevTicks)  * 100);

        // Devuelve el uso de la CPU formateado como un String
        return String.format("%.2f", cpuUsage).replace(',', '.');
    }
}
