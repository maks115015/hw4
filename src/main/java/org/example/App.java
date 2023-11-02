package org.example;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class App {

    public static void main( String[] args ) throws InterruptedException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.PUBLIC_ONLY);

        ExchangeRateProvider exchangeRateProvider = new ExchangeRateProvider(objectMapper);
        EventsSender eventsSender = new EventsSender(objectMapper);

        while (true) {
            try {
                String usdRate = exchangeRateProvider.getUsdRate();

                System.out.println("Current USD rate: " + usdRate);

                eventsSender.sendEvent(usdRate);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            TimeUnit.SECONDS.sleep(10);
        }

    }
}