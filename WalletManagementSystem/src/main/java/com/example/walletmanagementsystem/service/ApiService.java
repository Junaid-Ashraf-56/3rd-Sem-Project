package com.example.walletmanagementsystem.service;

import com.example.walletmanagementsystem.model.Crypto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ApiService {
    public static Crypto getCrypto(String coinId){
        String apiUrl = "https://api.coingecko.com/api/v3/simple/price?ids=\" + coinId + \"&vs_currencies=usd";
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line= bufferedReader.readLine())!=null){
                response.append(line);
            }
            bufferedReader.close();



            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.toString());

            double price = root.path(coinId).path("usd").asDouble();


            return new Crypto(coinId.toUpperCase(), capitalize(coinId), price, "CoinGecko");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    private static String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
