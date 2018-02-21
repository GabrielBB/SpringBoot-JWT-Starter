package com.claro.moose.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class MultiDatasourceConfig {

    /*
    NetCracker and other databases should be implemented here, same as PCE
     */

    @Bean(name = "pceDatasource")
    @ConfigurationProperties(prefix="spring.pce")
    public DataSource pceDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "pceJdbcTemplate")
    @Autowired
    public JdbcTemplate pceJdbcTemplate(@Qualifier("pceDatasource") DataSource dsMaster) {
        return new JdbcTemplate(dsMaster);
    }
}