package org.example.RecogerDatos;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

public class cargaCPU {
    public String obtenerCargaCPU() {
        // Espera un segundo antes de comenzar a recopilar datos
//        Util.sleep(1000);

        //Lee los datos de la CPU
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        CentralProcessor processor = hardware.getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
//        Util.sleep(1000);

        //Castea los datos para mostrar un porcentaje
        double cpuUsage = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        return "Uso de la CPU: " + String.format("%.0f", cpuUsage) + " %";
    }
}
