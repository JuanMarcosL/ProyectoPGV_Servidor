package org.example.RecogerDatos;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

/**
 * Clase que se encarga de obtener el uso de la memoria RAM.
 */
public class UsoRam {

    /**
     * Método que obtiene el uso actual de la memoria RAM.
     * @return El uso de la RAM como un String en porcentaje.
     */
    public String obtenerUsoRAM() {
        // Recopila información de la RAM
        SystemInfo si = new SystemInfo();
        GlobalMemory memory = si.getHardware().getMemory();
        long totalMemory = memory.getTotal();
        long availableMemory = memory.getAvailable();
        long usedMemory = totalMemory - availableMemory;
        double usedPercentage = (double) usedMemory / totalMemory * 100;

        // Devuelve el uso de la RAM formateado como un String
        return String.format("%.2f", usedPercentage).replace(',', '.');
    }
}
