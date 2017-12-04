package com.claro.moose.Factory;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.sql.CallableStatement;

/**
 * Created by Alenkart Rodriguez on 12/1/2017.
 */
public class StoredProcedureFactory {


    SimpleJdbcCall jdbcCall;

    StoredProcedureFactory (JdbcTemplate jdbcTemplate) {
        jdbcCall = new SimpleJdbcCall(jdbcTemplate);
    }

    public SimpleJdbcCall getStoredProcedure() {
        return jdbcCall;
    }

    public void setSchema(String schema) {
        jdbcCall.withSchemaName(schema);
    }

    public void setPackage(String _package) {
        jdbcCall.withCatalogName(_package);
    }

    public void setProcedure(String procedure) {
        jdbcCall.withProcedureName(procedure);
    }
}
