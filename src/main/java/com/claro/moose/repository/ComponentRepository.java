package com.claro.moose.repository;

import com.claro.moose.models.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alenkart Rodriguez on 12/1/2017.
 */

@Repository
public class ComponentRepository {

    @Autowired
    @Qualifier("mooseJdbcTemplate")
    private JdbcTemplate mooseJdbcTemplate;

    public List<Component> getComponents() {

        List<Component> components;
        String query = "SELECT id, name FROM COMPONENT";

        return mooseJdbcTemplate.query(query, (resultSet, i) -> {
            Component component = new Component();
            component.setId(resultSet.getInt("id"));
            return component;
        });
    }

    public Component getComponent() {

        Component component;
        String query = "SELECT id, name FROM COMPONENT";
        return new Component();
    }
}
