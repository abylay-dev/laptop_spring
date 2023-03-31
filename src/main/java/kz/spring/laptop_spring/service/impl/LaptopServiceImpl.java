package kz.spring.laptop_spring.service.impl;

import kz.spring.laptop_spring.model.Country;
import kz.spring.laptop_spring.model.Laptop;
import kz.spring.laptop_spring.repository.CountryRepo;
import kz.spring.laptop_spring.repository.LaptopRepo;
import kz.spring.laptop_spring.service.LaptopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaptopServiceImpl implements LaptopService {

    @Autowired
    private LaptopRepo laptopRepository;

    @Autowired
    private CountryRepo countryRepository;

    @Override
    public void upsertLaptop(Laptop l, Integer countryId) {
        Country country = countryRepository.getReferenceById(countryId);
        l.setCountry(country);
        laptopRepository.save(l);
    }

    @Override
    public Laptop getLaptopById(Integer id) {
        return laptopRepository.getReferenceById(id);
    }

    @Override
    public List<Laptop> getAllLaptops() {
        System.out.println("GET ALL LAPTOPS");
        return laptopRepository.findAll();
    }

    @Override
    public List<Laptop> getAllLaptopsOrderByCountDesc() {
        return laptopRepository.findAllByOrderByCountDesc();
    }

    @Override
    public List<Laptop> getAllLaptopsOrderByModelAsc() {
        return laptopRepository.findAllByOrderByModelAsc();
    }

    @Override
    public List<Laptop> getAllLaptopsPriceBetween(int min, int max) {
        return laptopRepository.findAllByPriceBetween(min, max);
    }

    @Override
    public void deleteLaptop(Integer id) {
        laptopRepository.deleteById(id);
    }
}
