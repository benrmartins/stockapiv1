package com.careerdevs.stockapiv1.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Overview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false, unique = true)
    private long Id;

    @JsonProperty("Symbol")
    @Column(name="symbol", nullable = false, unique = true)
    private String symbol;

    @JsonProperty("AssetType")
    @Column(name="asset_type", nullable = false)
    private String assetType;

    @JsonProperty("Name")
    @Column(name="name", nullable = false, unique = true)
    private String name;

    @JsonProperty("Exchange")
    @Column(name="exchange", nullable = false)
    private String exchange;

    @JsonProperty("Currency")
    @Column(name="currency", nullable = false)
    private String currency;

    @JsonProperty("Country")
    @Column(name="country", nullable = false)
    private String country;

    @JsonProperty("Sector")
    @Column(name="sector", nullable = false)
    private String sector;

    @JsonProperty("Industry")
    @Column(name="industry", nullable = false)
    private String industry;

    @JsonProperty("MarketCapitalization")
    @Column(name="market_cap", nullable = false)
    private Long marketCap;

    @JsonProperty("52WeekHigh")
    @Column(name="year_high", nullable = false)
    private Float yearHigh;

    @JsonProperty("52WeekLow")
    @Column(name="year_low", nullable = false)
    private Float yearLow;

    @JsonProperty("DividendDate")
    @Column(name="dividend_date", nullable = false)
    private String dividendDate;

    public long getId() {
        return Id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getAssetType() {
        return assetType;
    }

    public String getName() {
        return name;
    }

    public String getExchange() {
        return exchange;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCountry() {
        return country;
    }

    public String getSector() {
        return sector;
    }

    public String getIndustry() {
        return industry;
    }

    public long getMarketCap() {
        return marketCap;
    }

    public Float getYearHigh() {
        return yearHigh;
    }

    public Float getYearLow() {
        return yearLow;
    }

    public String getDividendDate() {
        return dividendDate;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"Id\":").append(Id);
        sb.append(", \"symbol\":\"").append(symbol).append('"');
        sb.append(", \"AssetType\":\"").append(assetType).append('"');
        sb.append(", \"name\":\"").append(name).append('"');
        sb.append(", \"exchange\":\"").append(exchange).append('"');
        sb.append(", \"currency\":\"").append(currency).append('"');
        sb.append(", \"country\":\"").append(country).append('"');
        sb.append(", \"sector\":\"").append(sector).append('"');
        sb.append(", \"industry\":\"").append(industry).append('"');
        sb.append(", \"marketCap\":\"").append(marketCap).append('"');
        sb.append(", \"yearHigh\":\"").append(yearHigh).append('"');
        sb.append(", \"yearLow\":\"").append(yearLow).append('"');
        sb.append('}');
        return sb.toString();
    }
}
