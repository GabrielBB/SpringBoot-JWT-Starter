package com.claro.moose.repositories;

import com.claro.moose.models.Domain;

import org.springframework.data.repository.CrudRepository;

public interface DomainRepository extends CrudRepository<Domain, Integer> {

    Domain findByName(String name);

}
