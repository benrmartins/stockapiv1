package com.careerdevs.stockapiv1.controllers;


import com.careerdevs.stockapiv1.models.Overview;
import com.careerdevs.stockapiv1.repositories.OverviewRespository;
import com.careerdevs.stockapiv1.utils.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/overview")
public class OverviewController {

    @Autowired
    private Environment env;

    @Autowired
    private OverviewRespository overviewRespository;

    private final String BASE_URL = "https://www.alphavantage.co/query?function=OVERVIEW";

    @GetMapping("/{symbol}")
    public ResponseEntity<?> dynamicOverview(RestTemplate restTemplate, @PathVariable String symbol) {
        try {
            String url = BASE_URL + "&symbol="+symbol+"&apikey="+env.getProperty("AV_API_KEY");

            Overview alphaVantageResponse = restTemplate.getForObject(url, Overview.class);

            if(alphaVantageResponse == null) {
                ApiError.throwErr(500,"Did not recieve response from AV" );
            } else if (alphaVantageResponse.getSymbol() == null) {
                ApiError.throwErr(404,"Invalid stock Symbol " + symbol);
            }

            return ResponseEntity.ok(alphaVantageResponse);

        } catch(Exception e) {
            return ApiError.genericApiError(e);
        }
    }


    @PostMapping("/{symbol}")
    public ResponseEntity<?> uploadOverviewBySymbol(RestTemplate restTemplate, @PathVariable String symbol) {
        try {
            String url = BASE_URL + "&symbol="+symbol+"&apikey="+env.getProperty("AV_API_KEY");

            Overview alphaVantageResponse = restTemplate.getForObject(url, Overview.class);

            if(alphaVantageResponse == null) {
                ApiError.throwErr(500,"Did not recieve response from AV" );
            } else if (alphaVantageResponse.getSymbol() == null) {
                ApiError.throwErr(404,"Invalid stock Symbol " + symbol);
            }

            Overview savedOverview = overviewRespository.save(alphaVantageResponse);

            return ResponseEntity.ok(savedOverview);

        } catch(HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        } catch(DataIntegrityViolationException e) {
            return ApiError.customApiError("Can not upload duplicate Stock data", 400);

        } catch(IllegalArgumentException e) {
            return ApiError.customApiError("URL is not absolute. Check URL", 500);

        } catch(Exception e) {
            return ApiError.genericApiError(e);
        }
    }

    @PostMapping("/testUpload")
    public ResponseEntity<?> uploadTestOverview(RestTemplate restTemplate) {
        try {

            String[] testSymbols = {"GS", "GOOG", "AAPL", "TM"};
            ArrayList<Overview> overviews = new ArrayList<>();
            for(int i = 0; i < testSymbols.length; i++) {
                String symbol = testSymbols[i];

                String apiKey =  env.getProperty("AV_API_KEY");
                String url = BASE_URL + "&symbol=" + symbol + "&apikey=" + apiKey;

                Overview alphaVantageResponse = restTemplate.getForObject(url, Overview.class);

                if(alphaVantageResponse == null) {
                    ApiError.throwErr(500,"Did not recieve response from AV" );
                } else if (alphaVantageResponse.getSymbol() == null) {
                    ApiError.throwErr(404,"Invalid stock Symbol " + symbol);
                }
                overviews.add(alphaVantageResponse);
            }

            Iterable<Overview> savedOverview = overviewRespository.saveAll(overviews);

            return ResponseEntity.ok(savedOverview);

        } catch(HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        } catch(DataIntegrityViolationException e) {
            return ApiError.customApiError("Can not upload duplicate Stock data", 400);

        } catch(Exception e) {
            return ApiError.genericApiError(e);
        }
    }

    @GetMapping("/all")
    private ResponseEntity<?> getAllOverviews() {
        try {
            Iterable<Overview> allOverviews = overviewRespository.findAll();
            return ResponseEntity.ok(allOverviews);

        } catch(HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        } catch(Exception e) {
            return ApiError.genericApiError(e);
        }
    }

    @DeleteMapping("/all")
    private ResponseEntity<?> deleteAllOverviews() {
        try {
            long allOverviewsCount = overviewRespository.count();

            if(allOverviewsCount == 0) return ResponseEntity.ok("No overviews to delete");

            overviewRespository.deleteAll();

            return ResponseEntity.ok("Deleted Overviews " + allOverviewsCount);

        } catch(HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        } catch(Exception e) {
            return ApiError.genericApiError(e);
        }
    }

    @GetMapping("/{field}/{value}")
    private ResponseEntity<?> getOverviewByField(@PathVariable String field, @PathVariable String value) {
        try {
            List<Overview> foundOverview = null;

            field = field.toLowerCase();

            switch (field) {
                case "id" -> foundOverview = overviewRespository.findById(Long.parseLong(value));
                case "symbol" -> foundOverview = overviewRespository.findBySymbol(value);
                case "sector" -> foundOverview = overviewRespository.findBySector(value);
                case "name" -> foundOverview = overviewRespository.findByName(value);
                case "current" -> foundOverview = overviewRespository.findByCurrency(value);
                case "country" -> foundOverview = overviewRespository.findByCountry(value);
                case "marketcapgt" -> foundOverview = overviewRespository.findByMarketCapGreaterThanEqual(Long.parseLong(value));
                case "marketcaplt" -> foundOverview = overviewRespository.findByMarketCapLessThanEqual(Long.parseLong(value));
                case "assettype" -> foundOverview = overviewRespository.findByAssetType(value);
                case "exchange" -> foundOverview = overviewRespository.findByExchange(value);
                case "yearhighgt" -> foundOverview = overviewRespository.findByYearHighGreaterThanEqual(Float.parseFloat(value));
                case "yearhighlt" -> foundOverview = overviewRespository.findByYearHighLessThanEqual(Float.parseFloat(value));
                case "yearlowgt" -> foundOverview = overviewRespository.findByYearLowGreaterThanEqual(Float.parseFloat(value));
                case "yearlowlt" -> foundOverview = overviewRespository.findByYearLowLessThanEqual(Float.parseFloat(value));
                case "ddafter" -> foundOverview = overviewRespository.findByDividendDateGreaterThanEqual(value);
                case "ddbefore" -> foundOverview = overviewRespository.findByDividendDateLessThanEqual(value);


            }

            if(foundOverview == null || foundOverview.isEmpty()) {
                ApiError.throwErr(404,   " did not match any overview");
            }

            return ResponseEntity.ok(foundOverview);


        } catch(HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        } catch(Exception e) {
            return ApiError.genericApiError(e);
        }
    }

    @DeleteMapping("/{field}/{value}")
    private ResponseEntity<?> deleteOverviewByField(@PathVariable String field, @PathVariable String value) {
        try {
            List<Overview> foundOverview = null;

            field = field.toLowerCase();

            switch (field) {
                case "id" -> foundOverview = overviewRespository.deleteById(Long.parseLong(value));
                case "symbol" -> foundOverview = overviewRespository.deleteBySymbol(value);
                case "sector" -> foundOverview = overviewRespository.deleteBySector(value);
                case "name" -> foundOverview = overviewRespository.deleteByName(value);
                case "currency" -> foundOverview = overviewRespository.deleteByCurrency(value);
                case "country" -> foundOverview = overviewRespository.deleteByCountry(value);
                case "marketcapgt" -> foundOverview = overviewRespository.deleteByMarketCapGreaterThanEqual(Long.parseLong(value));
                case "marketcaplt" -> foundOverview = overviewRespository.deleteByMarketCapLessThanEqual(Long.parseLong(value));
                case "assettype" -> foundOverview = overviewRespository.deleteByAssetType(value);
                case "exchange" -> foundOverview = overviewRespository.deleteByExchange(value);
                case "yearhighgt" -> foundOverview = overviewRespository.deleteByYearHighGreaterThanEqual(Float.parseFloat(value));
                case "yearhighlt" -> foundOverview = overviewRespository.deleteByYearHighLessThanEqual(Float.parseFloat(value));
                case "yearlowgt" -> foundOverview = overviewRespository.deleteByYearLowGreaterThanEqual(Float.parseFloat(value));
                case "yearlowlt" -> foundOverview = overviewRespository.deleteByYearLowLessThanEqual(Float.parseFloat(value));
                case "ddafter" -> foundOverview = overviewRespository.deleteByDividendDateGreaterThanEqual(value);
                case "ddbefore" -> foundOverview = overviewRespository.deleteByDividendDateLessThanEqual(value);
            }

            if(foundOverview == null || foundOverview.isEmpty()) {
                ApiError.throwErr(404,   " did not match any overview");
            }

            return ResponseEntity.ok(foundOverview);


        } catch(HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        } catch(Exception e) {
            return ApiError.genericApiError(e);
        }
    }

}
