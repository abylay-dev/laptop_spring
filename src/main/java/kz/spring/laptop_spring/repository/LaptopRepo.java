package kz.spring.laptop_spring.repository;

import kz.spring.laptop_spring.model.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaptopRepo extends JpaRepository<Laptop, Integer> {
    List<Laptop> findAllByPriceBetween(int min, int max);

    List<Laptop> findAllByOrderByCountDesc();

    List<Laptop> findAllByOrderByModelAsc();
}
