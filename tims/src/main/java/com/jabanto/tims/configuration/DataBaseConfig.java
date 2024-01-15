package com.jabanto.tims.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataBaseConfig {

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    public String getDatabaseFilePath() {
        // Suponiendo que la URL tiene el formato jdbc:sqlite:file_path
        // Donde "file_path" es la ruta del archivo
        String[] parts = databaseUrl.split(":");
        if (parts.length == 3 && "sqlite".equals(parts[1])) {
            return parts[2];
        } else {
            // Manejar el formato de URL desconocido o incorrecto
            return null;
        }
    }
}
