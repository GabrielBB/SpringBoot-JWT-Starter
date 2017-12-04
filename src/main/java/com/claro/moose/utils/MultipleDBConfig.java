package com.claro.moose.utils;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class MultipleDBConfig {

    // ============================ Moose ============================

    @Primary
    @Bean(name = "moose")
    @ConfigurationProperties(prefix = "spring.moose.datasource")
    public DataSource mooseDataSource() {

        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mooseJdbcTemplate")
    public JdbcTemplate mooseJdbcTemplate(@Qualifier("moose") DataSource moose) {

        return new JdbcTemplate(moose);
    }

    // ============================ OMS PCE ============================

    @Bean(name = "omsPce")
    @ConfigurationProperties(prefix = "spring.omsPce.datasource")
    public DataSource omsPceDataSource() {

        return DataSourceBuilder.create().build();
    }

    @Bean(name = "omsPceJdbcTemplate")
    public JdbcTemplate omsPceJdbcTemplate(@Qualifier("omsPce") DataSource omsPce) {

        return new JdbcTemplate(omsPce);
    }
}