package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Clock;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class EventsSender {

    private static final String URL =
            "https://www.google-analytics.com/mp/collect?measurement_id=G-1TCDK1TPRJ&api_secret=Rtl63DzYTp6JZahSsaW0zw";

    private static final String CLIENT_ID = "942056848.1698860528";
    private static final String USD_RATE = "usd_rate";
    private static final String EVENT_NAME = "usd_rate_update1";

    private final ObjectMapper objectMapper;

    public void sendEvent(String usdRate) {
        Map<String, Object> params =
                Map.of(USD_RATE, usdRate);

        Event event = Event.of(EVENT_NAME, params);

        GaRequest gaRequest = GaRequest.of(CLIENT_ID, List.of(event));

        doSend(gaRequest);
    }

    @SneakyThrows
    private void doSend(GaRequest gaRequest) {
        String requestStr = objectMapper.writeValueAsString(gaRequest);
        System.out.println("Created new request to send to GA: " + requestStr);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestStr))
                .build();

        HttpResponse<String> response =
                HttpClient.newBuilder()
                        .build()
                        .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Event was sent to GA with status: " + response.statusCode());
        System.out.println(Clock.systemDefaultZone().millis());
        System.out.println();
    }
}



