package com.example.walletmanagementsystem.service;

import com.example.walletmanagementsystem.model.Crypto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ApiService {
    public static Crypto getCrypto(String coinId){
        coinId = coinId.toLowerCase(); // normalize input
        String apiUrl = "https://api.coingecko.com/api/v3/simple/price?ids=" + coinId + "&vs_currencies=usd";

        try {
            System.out.println("DEBUG: API URL = " + apiUrl);
            URL url = new URL(apiUrl);
            JsonNode root = getJsonNode(url);
            System.out.println("DEBUG: Raw JSON = " + root.toPrettyString());

            double price = root.path(coinId).path("usd").asDouble();
            return new Crypto(coinId.toUpperCase(), capitalize(coinId), price, "CoinGecko");
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static JsonNode getJsonNode(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

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


    private static String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
