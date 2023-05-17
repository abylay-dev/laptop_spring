package kz.spring.laptop_spring.repository;

import kz.spring.laptop_spring.model.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketRepo extends JpaRepository<Market, Integer> {
//    List<Market> ;
}
