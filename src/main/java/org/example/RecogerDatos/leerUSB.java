package org.example.RecogerDatos;

import java.nio.file.FileStore;
import java.nio.file.FileSystems;

public class leerUSB {
    public String obtenerAlmacenamiento() {
        StringBuilder storageInfo = new StringBuilder();

        try {
            // Obtener todos los FileStore disponibles
            Iterable<FileStore> fileStores = FileSystems.getDefault().getFileStores();

            // Iterar sobre los FileStore
            for (FileStore fileStore : fileStores) {
                try {
                    // Imprimir información sobre cada FileStore
                    storageInfo.append("Nombre Disco: ").append(fileStore).append(" ");
                    storageInfo.append(",Tipo: ").append(fileStore.type()).append(" ");

                    // Obtener el porcentaje de uso de almacenamiento
                    double usagePercentage = porcentajeDeUso(fileStore);
                    storageInfo.append(",Porcentaje de uso: ").append(String.format("%.2f", usagePercentage).replace(',', '.')).append("% ,");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Devolver la información almacenada como un String
            return storageInfo.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al obtener la información del almacenamiento.";
        }
    }

    /**
     * Obtiene el porcentaje de uso de almacenamiento para un FileStore específico.
     *
     * @param fileStore FileStore para el cual se desea obtener el porcentaje de uso.
     * @return Porcentaje de uso de almacenamiento para el FileStore.
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
