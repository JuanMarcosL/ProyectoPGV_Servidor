package org.example.RecogerDatos;

import java.nio.file.FileStore;
import java.nio.file.FileSystems;

/**
 * Clase que se encarga de obtener información sobre el almacenamiento en disco.
 */
public class Discos {
    private String storageInfo;

    /**
     * Método que obtiene la información del almacenamiento en disco.
     * @return Una cadena que contiene información sobre el almacenamiento en disco.
     */
    public String obtenerAlmacenamiento() {
        StringBuilder storageInfoBuilder = new StringBuilder();

        try {
            // Obtener todos los FileStore disponibles
            Iterable<FileStore> fileStores = FileSystems.getDefault().getFileStores();

            // Iterar sobre los FileStore
            for (FileStore fileStore : fileStores) {
                try {
                    // Agregar información sobre cada FileStore al StringBuilder
                    storageInfoBuilder.append(fileStore).append("_");
                    storageInfoBuilder.append(fileStore.type()).append("_");
                    storageInfoBuilder.append(fileStore.getTotalSpace()).append("_");
                    double usagePercentage = porcentajeDeUso(fileStore);
                    storageInfoBuilder.append(String.format("%.2f", usagePercentage).replace(',', '.')).append("#");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Eliminar el último delimitador '#'
            if (storageInfoBuilder.length() > 0) {
                storageInfoBuilder.setLength(storageInfoBuilder.length() - 1);
            }

            // Almacenar la información obtenida como una cadena
            storageInfo = storageInfoBuilder.toString();
            return storageInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al obtener la información del almacenamiento.";
        }
    }

    /**
     * Método que devuelve la información de almacenamiento actual.
     * @return La información de almacenamiento como una cadena.
     */
    public String getStorageInfo() {
        return storageInfo;
    }

    /**
     * Método estático para calcular el porcentaje de uso del disco.
     * @param fileStore El FileStore para el que se calculará el porcentaje de uso.
     * @return El porcentaje de uso del disco como un valor double.
     */
    private static double porcentajeDeUso(FileStore fileStore) {
        try {
            long totalSpace = fileStore.getTotalSpace();
            long usedSpace = totalSpace - fileStore.getUsableSpace();

            if (totalSpace > 0) {
                return (usedSpace / (double) totalSpace) * 100.0;
            } else {
                return 0.0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}
