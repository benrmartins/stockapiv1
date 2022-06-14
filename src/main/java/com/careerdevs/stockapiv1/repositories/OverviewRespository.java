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


    public List<Overview> deleteById(long id);

    public List<Overview> deleteBySymbol(String symbol);

    public List<Overview> deleteBySector(String sector);

    public List<Overview> deleteByName(String name);

    public List<Overview> deleteByCurrency(String currency);

    public List<Overview> deleteByCountry(String country);


//    Optional<Overview> findBySymbol(String symbol);
//    List<Overview> findByExchange(String exchange);



}
