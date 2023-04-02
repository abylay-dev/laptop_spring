package kz.spring.laptop_spring.service.impl;

import kz.spring.laptop_spring.model.Market;
import kz.spring.laptop_spring.repository.MarketRepo;
import kz.spring.laptop_spring.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketServiceImpl implements MarketService {
    @Autowired
    private MarketRepo marketRepository;

    @Override
    public List<Market> getAllMarkets() {
        return marketRepository.findAll();
    }

    @Override
    public Market getMarket(Integer id) {
        return marketRepository.getReferenceById(id);
    }
}
