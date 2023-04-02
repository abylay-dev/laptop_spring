package kz.spring.laptop_spring.service;

import kz.spring.laptop_spring.model.Market;

import java.util.List;

public interface MarketService {
    List<Market> getAllMarkets();

    Market getMarket(Integer id);
}
