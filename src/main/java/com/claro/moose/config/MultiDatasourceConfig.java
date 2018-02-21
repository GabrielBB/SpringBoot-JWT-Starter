package com.claro.moose.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class MultiDatasourceConfig {

    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "pceDatasource")
    @ConfigurationProperties(prefix="spring.pce.datasource")
    public DataSource pceDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "pceJdbcTemplate")
    @Autowired
    public JdbcTemplate pceJdbcTemplate(@Qualifier("pceDatasource") DataSource dsMaster) {
        return new JdbcTemplate(dsMaster);
    }
}