//package org.example.RecogerDatos;
//
//import oshi.SystemInfo;
//import oshi.hardware.CentralProcessor;
//import oshi.hardware.HardwareAbstractionLayer;
//
//public class cargaCPU {
//
//    private static double cpuUsage;
//    public String obtenerCargaCPU() {
//        // Espera un segundo antes de comenzar a recopilar datos
////        Util.sleep(1000);
//
//        //Lee los datos de la CPU
//        SystemInfo systemInfo = new SystemInfo();
//        HardwareAbstractionLayer hardware = systemInfo.getHardware();
//        CentralProcessor processor = hardware.getProcessor();
//        long[] prevTicks = processor.getSystemCpuLoadTicks();
////        Util.sleep(1000);
//
//        //Castea los datos para mostrar un porcentaje
//        cpuUsage = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
//        return String.valueOf((int)(cpuUsage));
//    }
//}


package org.example.RecogerDatos;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

public class cargaCPU {

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

        // Castea los datos para mostrar un porcentaje
        double cpuUsage = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;

        // Devuelve el uso de la CPU como un String
        return String.format("%.2f", cpuUsage).replace(',', '.');
    }
}