package com.lambdaschool.countries.repositories;

import com.lambdaschool.countries.modules.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Long> {
}
