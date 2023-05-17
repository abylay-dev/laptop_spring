package kz.spring.laptop_spring.service;

import kz.spring.laptop_spring.model.Market;

import java.util.List;

public interface MarketService {
    List<Market> getAllMarkets();

    Market getMarket(Integer id);

    public boolean editMarket(Integer id, String name);

    boolean deleteMarketById(Integer id);

    void addMarket(String name);
}
