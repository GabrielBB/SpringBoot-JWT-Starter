package com.claro.moose.repositories;

import com.claro.moose.models.PCEVersion;

import org.springframework.data.repository.CrudRepository;


/**
 * Created by Alenkart Rodriguez on 2/19/2018.
 */
public interface PCEVersionRepository extends CrudRepository<PCEVersion, Long> {

    PCEVersion findFirstByOrderByIdDesc();

}
