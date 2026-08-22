package com.martinatto.autos.autos_backend.shared.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path carpetaAutos = Paths.get("/uploads/autos");

    public FileStorageService() {
        try {
            Files.createDirectories(carpetaAutos);
        } catch (IOException e) {
            throw new RuntimeException(
                    "No se pudo crear la carpeta de imágenes", e
            );
        }
    }

    public String guardarImagen(MultipartFile imagen) {
        if (imagen == null || imagen.isEmpty()) {
            throw new RuntimeException(
                    "La imagen es obligatoria"
            );
        }

        try {
            String nombreArchivo = Paths.get(imagen.getOriginalFilename())
                    .getFileName()
                    .toString();

            Path destino = carpetaAutos.resolve(nombreArchivo);

            Files.copy(
                    imagen.getInputStream(),
                    destino,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return "/uploads/autos/" + nombreArchivo;

        } catch (IOException e) {
            throw new RuntimeException(
                    "No se pudo guardar la imagen", e
            );
        }
    }
}