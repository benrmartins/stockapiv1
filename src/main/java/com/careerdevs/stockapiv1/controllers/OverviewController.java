package com.careerdevs.stockapiv1.controllers;


import com.careerdevs.stockapiv1.models.Overview;
import com.careerdevs.stockapiv1.repositories.OverviewRespository;
import com.careerdevs.stockapiv1.utils.ApiErrorHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/overview")
public class OverviewController {

    @Autowired
    private Environment env;

    @Autowired
    private OverviewRespository overviewRespository;

    private final String BASE_URL = "https://www.alphavantage.co/query?function=OVERVIEW";

    @GetMapping("/test")
    public ResponseEntity<?> testOverview(RestTemplate restTemplate) {
        try {
            String url = BASE_URL + "&symbol=IBM&apikey=demo";

            Overview alphaVantageResponse = restTemplate.getForObject(url, Overview.class);

            return ResponseEntity.ok(alphaVantageResponse);

        } catch(IllegalArgumentException e) {
            return ApiErrorHandling.customApiError("URL is not absolute. Check URL", HttpStatus.INTERNAL_SERVER_ERROR);

        } catch(Exception e) {
            return ApiErrorHandling.genericApiError(e);
        }
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<?> dynamicOverview(RestTemplate restTemplate, @PathVariable String symbol) {
        try {
            String url = BASE_URL + "&symbol="+symbol+"&apikey="+env.getProperty("AV_API_KEY");

            Overview alphaVantageResponse = restTemplate.getForObject(url, Overview.class);

            if(alphaVantageResponse == null) {
                return ApiErrorHandling.customApiError("Did not recieve response from AV", HttpStatus.INTERNAL_SERVER_ERROR);
            } else if (alphaVantageResponse.getSymbol() == null) {
                return ApiErrorHandling.customApiError("Invalid stock Symbol " + symbol, HttpStatus.NOT_FOUND);
            }

            return ResponseEntity.ok(alphaVantageResponse);

        } catch(Exception e) {
            return ApiErrorHandling.genericApiError(e);
        }
    }

    @PostMapping("/test")
    public ResponseEntity<?> testUploadOverview(RestTemplate restTemplate) {
        try {
            String url = BASE_URL + "&symbol=IBM&apikey=demo";

            Overview alphaVantageResponse = restTemplate.getForObject(url, Overview.class);

            if(alphaVantageResponse == null) {
                return ApiErrorHandling.customApiError("Did not recieve response from AV", HttpStatus.INTERNAL_SERVER_ERROR);
            } else if (alphaVantageResponse.getSymbol() == null) {
                return ApiErrorHandling.customApiError("No Data Retrieved from AV", HttpStatus.NOT_FOUND);
            }

            Overview savedOverview = overviewRespository.save(alphaVantageResponse);

            return ResponseEntity.ok(savedOverview);

        } catch(DataIntegrityViolationException e) {
            return ApiErrorHandling.customApiError("Can not upload duplicate Stock data", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(IllegalArgumentException e) {
            return ApiErrorHandling.customApiError("URL is not absolute. Check URL", HttpStatus.INTERNAL_SERVER_ERROR);

        } catch(Exception e) {
            return ApiErrorHandling.genericApiError(e);
        }
    }
    @PostMapping("/{symbol}")
    public ResponseEntity<?> uploadOverviewBySymbol(RestTemplate restTemplate, @PathVariable String symbol) {
        try {
            String url = BASE_URL + "&symbol="+symbol+"&apikey="+env.getProperty("AV_API_KEY");

            Overview alphaVantageResponse = restTemplate.getForObject(url, Overview.class);

            if(alphaVantageResponse == null) {
                return ApiErrorHandling.customApiError("Did not recieve response from AV", HttpStatus.INTERNAL_SERVER_ERROR);
            } else if (alphaVantageResponse.getSymbol() == null) {
                return ApiErrorHandling.customApiError("Invalid stock Symbol " + symbol, HttpStatus.NOT_FOUND);
            }

            Overview savedOverview = overviewRespository.save(alphaVantageResponse);

            return ResponseEntity.ok(savedOverview);

        } catch(DataIntegrityViolationException e) {
            return ApiErrorHandling.customApiError("Can not upload duplicate Stock data", HttpStatus.BAD_REQUEST);

        } catch(IllegalArgumentException e) {
            return ApiErrorHandling.customApiError("URL is not absolute. Check URL", HttpStatus.INTERNAL_SERVER_ERROR);

        } catch(Exception e) {
            return ApiErrorHandling.genericApiError(e);
        }
    }

    }
