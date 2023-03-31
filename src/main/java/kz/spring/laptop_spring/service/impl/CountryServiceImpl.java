package kz.spring.laptop_spring.service.impl;

import kz.spring.laptop_spring.model.Country;
import kz.spring.laptop_spring.repository.CountryRepo;
import kz.spring.laptop_spring.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepo countryRepository;

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Country getCountry(Integer id) {
        return countryRepository.getReferenceById(id);
    }

    @Override
    public void upsertCountry(Country country) {
        countryRepository.save(country);
    }

    @Override
    public void deleteCountry(Integer id) {
        countryRepository.deleteById(id);
    }
}
