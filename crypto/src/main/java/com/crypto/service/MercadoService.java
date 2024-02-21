package com.crypto.service;

import com.crypto.models.Mercado;
import com.crypto.repository.MercadoRepository;

import static java.lang.Integer.parseInt;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MercadoService {
    @Autowired
    private MercadoRepository mercadoRepository;

    private static final String API_URL_BINANCE = "https://api.coincap.io/v2/exchanges/binance";
    private static final String API_URL_KUCOIN = "https://api.coincap.io/v2/exchanges/kucoin";
    private static final String API_URL_KRAKEN = "https://api.coincap.io/v2/exchanges/kraken";
    private static final String JSON_FILE_PATH = "out/response/";

    public void coletarDadosDaAPI() throws Exception {
        requestAPI(API_URL_BINANCE, "binance_response.json");
        requestAPI(API_URL_KUCOIN, "kucoin_response.json");
        requestAPI(API_URL_KRAKEN, "kraken_response.json");
    }

    public void requestAPI(String url, String filename) throws IOException {
        URL APIurl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) APIurl.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            Mercado market = tratarResponse(connection, filename);
            Mercado marketExistent = mercadoRepository.findById(market.getNome()).orElse(null);
            if (marketExistent != null) {
                marketExistent.setRank(market.getRank());
                marketExistent.setParesTroca(market.getParesTroca());
                mercadoRepository.save(marketExistent);
            } else{
                mercadoRepository.save(market);
            }
        } else {
            System.out.println("ERROR in request of " + url);
        }
    }

    public Mercado tratarResponse(HttpURLConnection connection, String filename) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        String response = content.toString();
        createJson(response, filename);

        int marketNameStartIndex = response.indexOf("\"name\":") + 8;
        int marketNameEndIndex = response.indexOf("\"rank\":", marketNameStartIndex) - 2;

        int rankStartIndex = response.indexOf("\"rank\":") + 8;
        int rankEndIndex = response.indexOf(",", rankStartIndex) - 1;
        int marketRank = parseInt(response.substring(rankStartIndex, rankEndIndex));

        int tradingPairsStartIndex = response.indexOf("\"tradingPairs\":") + 16;
        int tradingPairsEndIndex = response.indexOf(",", tradingPairsStartIndex) - 1;
        int marketTradingPairs = parseInt(response.substring(tradingPairsStartIndex, tradingPairsEndIndex));

        if (marketNameStartIndex >= 0 && marketNameEndIndex >= 0 && marketNameEndIndex > marketNameStartIndex) {
            String marketName = response.substring(marketNameStartIndex, marketNameEndIndex);
            return new Mercado(marketName, marketRank, marketTradingPairs);
        } else {
            throw new RuntimeException("Failed to extract market name from response");
        }
    }

    private void createJson(String jsonString, String filename) {
        jsonString = jsonString.replaceAll("\\{", "{\n");
        jsonString = jsonString.replaceAll("\\}", "\n}");
        jsonString = jsonString.replaceAll("\\[", "[\n");
        jsonString = jsonString.replaceAll("\\]", "\n]");
        jsonString = jsonString.replaceAll(",", ",\n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(JSON_FILE_PATH + filename))) {
            writer.write(jsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
