package com.careerdevs.stockapiv1.repositories;

import com.careerdevs.stockapiv1.models.Overview;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OverviewRespository extends CrudRepository<Overview, Long> {

    Optional<Overview> findBySymbol(String symbol);

    List<Overview> findByExchange(String exchange);

}
