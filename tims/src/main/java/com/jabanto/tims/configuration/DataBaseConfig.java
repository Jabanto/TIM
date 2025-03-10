package com.jabanto.tims.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataBaseConfig {

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    public String getDatabaseFilePath() {
        // Assuming the URL has the format jdbc:sqlite:file_path
        // Where "file_path" is the file path
        String[] parts = databaseUrl.split(":");
        if (parts.length == 3 && "sqlite".equals(parts[1])) {
            return parts[2];
        } else {
            return null;
        }
    }
}
