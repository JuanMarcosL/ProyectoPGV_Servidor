package org.example.RecogerDatos;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class usoRAM {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private String usoRAMStr;  // Variable para almacenar la información de uso de RAM

    public void iniciarMonitorizacion() {
        // Programa la tarea para ejecutarse cada segundo
        scheduler.scheduleAtFixedRate(this::recogerYEnviarUsoRAM, 0, 1, TimeUnit.SECONDS);
    }

    private void recogerYEnviarUsoRAM() {
        // Recopila información de la CPU y la RAM
        SystemInfo si = new SystemInfo();
        GlobalMemory memory = si.getHardware().getMemory();

        // Carga de la memoria
        long memoriaUsada = memory.getTotal() - memory.getAvailable();
        double memoriaUsadaMB = memoriaUsada / (1024.0 * 1024.0);

        // Construir el mensaje final
        usoRAMStr = "Memoria RAM Usada: " + String.format("%.2f", memoriaUsadaMB).replace(',', '.') + " MB";
    }

    public String obtenerUsoRAM() {
        return usoRAMStr;
    }
}
