package com.careerdevs.stockapiv1.repositories;

import com.careerdevs.stockapiv1.models.Overview;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Transactional
public interface OverviewRespository extends CrudRepository<Overview, Long> {

    public List<Overview> findById(long id);
    public List<Overview> findBySymbol(String symbol);
    public List<Overview> findBySector(String sector);
    public List<Overview> findByName(String name);
    public List<Overview> findByCurrency(String currency);
    public List<Overview> findByCountry(String country);
    public List<Overview> findByAssetType(String assetType);
    public  List<Overview> findByExchange(String exchange);
    public List<Overview> findByYearHighGreaterThanEqual(Float yearHigh);
    public List<Overview> findByYearHighLessThanEqual(Float yearHigh);
    public List<Overview> findByMarketCapGreaterThanEqual(long marketCap);
    public List<Overview> findByMarketCapLessThanEqual(long marketCap);

    public List<Overview> findByYearLowGreaterThanEqual(Float yearLow);
    public List<Overview> findByYearLowLessThanEqual(Float yearLow);


    public List<Overview> deleteById(long id);
    public List<Overview> deleteBySymbol(String symbol);
    public List<Overview> deleteBySector(String sector);
    public List<Overview> deleteByName(String name);
    public List<Overview> deleteByCurrency(String currency);
    public List<Overview> deleteByCountry(String country);
    public List<Overview> deleteByMarketCapGreaterThanEqual(long marketCap);
    public List<Overview> deleteByMarketCapLessThanEqual(long marketCap);
    public List<Overview> deleteByAssetType(String assetType);
    public  List<Overview> deleteByExchange(String exchange);
    public List<Overview> deleteByYearHighGreaterThanEqual(Float yearHigh);
    public List<Overview> deleteByYearHighLessThanEqual(Float yearHigh);

    public List<Overview> deleteByYearLowGreaterThanEqual(Float yearLow);
    public List<Overview> deleteByYearLowLessThanEqual(Float yearLow);






}
