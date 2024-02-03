package org.example.RecogerDatos;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

public class usoRAM {

    public String obtenerUsoRAM() {
        // Recopila información de la RAM
        SystemInfo si = new SystemInfo();
        GlobalMemory memory = si.getHardware().getMemory();
        long totalMemory = memory.getTotal();
        long availableMemory = memory.getAvailable();
        long usedMemory = totalMemory - availableMemory;
        double usedPercentage = (double) usedMemory / totalMemory * 100;

        // Devuelve el uso de la RAM como un String
        return String.format("%.2f", usedPercentage).replace(',', '.');
    }
}
