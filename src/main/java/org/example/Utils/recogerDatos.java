package org.example.Utils;

import org.example.RecogerDatos.CargaCPU;
import org.example.RecogerDatos.Discos;
import org.example.RecogerDatos.UsoRam;
import org.example.RecogerDatos.VelocidadInternet;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Clase que se encarga de recoger y enviar datos del sistema.
 */
public class recogerDatos {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final CargaCPU obtenerCarga;
    private final UsoRam usoDeRAM;
    private final VelocidadInternet velocInternet;
    private final Discos lectorUSB;

    private static StringBuilder mensaje = new StringBuilder();

    /**
     * Constructor de la clase que inicializa los objetos para obtener los datos del sistema.
     */
    public recogerDatos() {
        obtenerCarga = new CargaCPU();
        usoDeRAM = new UsoRam();
        velocInternet = new VelocidadInternet();
        lectorUSB = new Discos();
    }

    /**
     * Método para obtener el mensaje.
     * @return El mensaje recopilado.
     */
    public static StringBuilder getMensaje() {
        return mensaje;
    }

    /**
     * Inicia la monitorización de los datos.
     */
    public void iniciarMonitorizacion() {
        scheduler.scheduleAtFixedRate(this::recogerYEnviarDatos, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * Recoge y envía los datos al cliente.
     */
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
