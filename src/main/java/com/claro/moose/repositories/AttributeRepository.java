package com.claro.moose.repositories;

import com.claro.moose.models.Attribute;
import org.springframework.data.repository.CrudRepository;

public interface AttributeRepository extends CrudRepository<Attribute, Long> {

    Attribute findByPropertyId(Integer propertyId);
}