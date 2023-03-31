package kz.spring.laptop_spring.service;

import kz.spring.laptop_spring.model.Laptop;

import java.util.List;

public interface LaptopService {
    void upsertLaptop(Laptop l, Integer countryId);

    Laptop getLaptopById(Integer id);

    List<Laptop> getAllLaptops();

    List<Laptop> getAllLaptopsOrderByCountDesc();

    List<Laptop> getAllLaptopsOrderByModelAsc();

    List<Laptop> getAllLaptopsPriceBetween(int min, int max);

    void deleteLaptop(Integer id);
}
