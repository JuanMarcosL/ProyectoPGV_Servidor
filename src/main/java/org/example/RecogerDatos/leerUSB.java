package org.example.RecogerDatos;

import java.nio.file.FileStore;
import java.nio.file.FileSystems;

public class leerUSB {
    private String storageInfo;

    public String obtenerAlmacenamiento() {
        StringBuilder storageInfoBuilder = new StringBuilder();

        try {
            // Obtener todos los FileStore disponibles
            Iterable<FileStore> fileStores = FileSystems.getDefault().getFileStores();

            // Iterar sobre los FileStore
            for (FileStore fileStore : fileStores) {
                try {
                    // Imprimir información sobre cada FileStore
                    storageInfoBuilder.append(fileStore).append("_");
                    storageInfoBuilder.append(fileStore.type()).append("_");
                    storageInfoBuilder.append(fileStore.getTotalSpace()).append("_");
                    double usagePercentage = porcentajeDeUso(fileStore);
                    storageInfoBuilder.append(String.format("%.2f", usagePercentage).replace(',', '.')).append("#");


                    // Obtener el porcentaje de uso de almacenamiento
//                    double usagePercentage = porcentajeDeUso(fileStore);
//                    storageInfoBuilder.append(String.format("%.2f", usagePercentage).replace(',', '.')).append("#");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Eliminar el último delimitador '#'
            if (storageInfoBuilder.length() > 0) {
                storageInfoBuilder.setLength(storageInfoBuilder.length() - 1);
            }

            // Devolver la información almacenada como un String
            storageInfo = storageInfoBuilder.toString();
            return storageInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al obtener la información del almacenamiento.";
        }
    }

    public String getStorageInfo() {
        return storageInfo;
    }

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