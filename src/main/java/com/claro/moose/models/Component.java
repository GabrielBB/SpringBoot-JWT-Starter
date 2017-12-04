package com.claro.moose.models;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Alenkart Rodriguez on 11/30/2017.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Component {
    private int id;
    private String code;
    private String name;
    private List<Attribute> attributes;
}
