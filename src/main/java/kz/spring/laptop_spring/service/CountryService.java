package kz.spring.laptop_spring.service;

import kz.spring.laptop_spring.model.Country;

import java.util.List;

public interface CountryService {
    List<Country> getAllCountries();

    Country getCountry(Integer id);

    void upsertCountry(Country country);

    void deleteCountry(Integer id);
}
