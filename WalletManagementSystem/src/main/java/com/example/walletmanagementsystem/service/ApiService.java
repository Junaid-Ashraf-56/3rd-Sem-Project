package com.example.walletmanagementsystem.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ApiService {
    private static final String API_URL = "https://api.coingecko.com/api/v3/simple/price?ids=%s&vs_currencies=usd";
    //"https://api.coingecko.com/api/v3/simple/price?ids=%s&vs_currencies=usd"
    private static final Map<String, Double> cachedPrices = new HashMap<>();

    public static Map<String, Double> getMultiplePrices(List<String> coinIds) {
        String joinedIds = coinIds.stream()
                .map(String::toLowerCase)
                .collect(Collectors.joining(","));

        String apiUrl = String.format(API_URL, joinedIds);
        System.out.println("Requesting: " + apiUrl);

        try {
            URL url = new URL(apiUrl);
            JsonNode root = getJsonNode(url);

            Map<String, Double> prices = new HashMap<>();
            for (String coin : coinIds) {
                double price = root.path(coin).path("usd").asDouble();
                if (price > 0) {
                    prices.put(coin, price);
                    cachedPrices.put(coin, price);
                }
            }
            System.out.println("CoinGecko Batch Response: " + root);

            return prices;

        } catch (Exception e) {
            System.err.println("API failed, using cached prices.");
            return new HashMap<>(cachedPrices);
        }
    }

    private static JsonNode getJsonNode(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = connection.getResponseCode();
        if (responseCode == 429) {
            System.out.println("⚠️ Rate limit hit (HTTP 429). Skipping this tick.");
            return null;
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            response.append(line);
        }
        bufferedReader.close();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(response.toString());
    }


    public static String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
