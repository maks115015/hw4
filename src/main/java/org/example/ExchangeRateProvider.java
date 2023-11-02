package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExchangeRateProvider {

    private static final String URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json&valcode=USD";

    private final ObjectMapper objectMapper;

    public String getUsdRate() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .header("accept", "application/json")
                .uri(URI.create(URL))
                .GET()
                .build();

        HttpResponse<String> response =
                HttpClient.newBuilder()
                        .build()
                        .send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IllegalStateException("Unexpected status code: " + response.statusCode());
        }

        return parseUsdRate(response.body());
    }

    private String parseUsdRate(String response) throws JsonProcessingException {
        return objectMapper.readTree(response).get(0).get("rate").asText();
    }
}
