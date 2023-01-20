package com.yudiprojects.firstplugin.config;


import com.yudiprojects.firstplugin.config.properties.DatabaseProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = "com.yudiprojects.firstplugin.repository")
public class DataBaseConfig {

    private final DatabaseProperties properties;




    private DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setUsername("postgres");
        config.setPassword("dreges228");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/plugin_db");
        DataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }
}
