package com.example.spring.kafka.producer.generator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractRandomStockQuoteGenerator {

    private final Random random = new Random();

    private final List<RandomStockQuoteGenerator.Instrument> instruments;
    private static final String STOCK_EXCHANGE_NASDAQ = "NASDAQ";
    private static final String STOCK_EXCHANGE_NEW_YORK = "NYSE";
    private static final String STOCK_EXCHANGE_AMSTERDAM = "AMS";

    private static final String CURRENCY_EURO = "EUR";
    private static final String CURRENCY_US_DOLLAR = "USD";

    AbstractRandomStockQuoteGenerator() {
        instruments = Arrays.asList(new RandomStockQuoteGenerator.Instrument("AAPL", STOCK_EXCHANGE_NASDAQ, CURRENCY_US_DOLLAR),
        new RandomStockQuoteGenerator.Instrument("AMZN", STOCK_EXCHANGE_NASDAQ, CURRENCY_US_DOLLAR),
        new RandomStockQuoteGenerator.Instrument("GOOGL", STOCK_EXCHANGE_NASDAQ, CURRENCY_US_DOLLAR),
        new RandomStockQuoteGenerator.Instrument("NFLX", STOCK_EXCHANGE_NASDAQ, CURRENCY_US_DOLLAR),
        new RandomStockQuoteGenerator.Instrument("INGA", STOCK_EXCHANGE_AMSTERDAM, CURRENCY_EURO),
        new RandomStockQuoteGenerator.Instrument("AD", STOCK_EXCHANGE_AMSTERDAM, CURRENCY_EURO),
        new RandomStockQuoteGenerator.Instrument("RDSA", STOCK_EXCHANGE_AMSTERDAM, CURRENCY_EURO),
        new RandomStockQuoteGenerator.Instrument("KO", STOCK_EXCHANGE_NEW_YORK, CURRENCY_US_DOLLAR));
    }

    BigDecimal generateRandomPrice() {
        double leftLimit = 1.000D;
        double rightLimit = 3000.000D;

        BigDecimal randomPrice = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(leftLimit, rightLimit));
        randomPrice = randomPrice.setScale(3, RoundingMode.HALF_UP);
        return randomPrice;
    }

    RandomStockQuoteGenerator.Instrument pickRandomInstrument() {

        int randomIndex = random.nextInt(instruments.size());
        return instruments.get(randomIndex);
    }


    record Instrument(String symbol, String exchange, String currency) {
    }
}
