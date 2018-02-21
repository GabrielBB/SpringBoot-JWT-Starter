package com.claro.moose.repositories;

import com.claro.moose.models.Entry;
import org.springframework.data.repository.CrudRepository;

public interface EntryRepository extends CrudRepository<Entry, Integer> {

}