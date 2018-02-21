package com.claro.moose.repositories;

import com.claro.moose.models.Component;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by Alenkart Rodriguez on 2/19/2018.
 */
public interface ComponentRepository extends CrudRepository<Component, Long> {

    Component findByCatalogId(Long catalogId);

}
