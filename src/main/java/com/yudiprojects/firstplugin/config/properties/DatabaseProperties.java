package com.yudiprojects.firstplugin.config.properties;

import lombok.Getter;

import java.io.IOException;
import java.util.Properties;

@Getter
public class DatabaseProperties {

    public void loadProperties() {
        Properties properties = new Properties();
        try {
            properties.load(DatabaseProperties.class.getResourceAsStream("/db.properties"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
