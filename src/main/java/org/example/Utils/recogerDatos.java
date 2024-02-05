package org.example.Utils;

import org.example.RecogerDatos.cargaCPU;
import org.example.RecogerDatos.leerUSB;
import org.example.RecogerDatos.usoRAM;
import org.example.RecogerDatos.velocidadInternet;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class recogerDatos {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final cargaCPU obtenerCarga;
    private final usoRAM usoDeRAM;
    private final velocidadInternet velocInternet;
    private final leerUSB lectorUSB;

    private static StringBuilder mensaje = new StringBuilder();

    public recogerDatos() {
        obtenerCarga = new cargaCPU();
        usoDeRAM = new usoRAM();
        velocInternet = new velocidadInternet();
        lectorUSB = new leerUSB();
    }

    public static StringBuilder getMensaje() {
        return mensaje;
    }

    public void iniciarMonitorizacion() {
        scheduler.scheduleAtFixedRate(this::recogerYEnviarDatos, 0, 1, TimeUnit.SECONDS);
    }

    private void recogerYEnviarDatos() {
        String cargaCPUStr = obtenerCarga.obtenerCargaCPU();
        String usoRAMStr = usoDeRAM.obtenerUsoRAM();
        String leerUSBStr = lectorUSB.obtenerAlmacenamiento();
        String velocidadInternetStr = (velocInternet.obtenerVelocidadInternet()) + "";

        mensaje.setLength(0);  // Limpiar el StringBuilder
        mensaje.append(usoRAMStr).append(",");
        mensaje.append(cargaCPUStr).append(",");
        mensaje.append("[");
        mensaje.append(leerUSBStr).append("],");
        mensaje.append(velocidadInternetStr);


        System.out.println(mensaje.toString());
    }
}