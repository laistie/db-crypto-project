package uel.br.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uel.br.project.dao.PgCryptomoedaDAO;
import uel.br.project.dao.PgMoedavolatilDAO;
import uel.br.project.model.Cryptomoeda;
import uel.br.project.model.Moedavolatil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@RestController
public class CryptomoedaController {
    @Autowired
    private PgCryptomoedaDAO pgCryptomoedaDAO;
    @Autowired
    private PgMoedavolatilDAO pgMoedavolatilDAO;

    private static final List<String> COINS = Arrays.asList("bitcoin", "ethereum", "ripple", "litecoin", "cardano", "tether", "tron", "binance-coin", "dogecoin", "binance-usd", "polygon", "usd-coin", "solana", "toncoin");

    @GetMapping("/API-request-moedas")
    public void APIRequestMoedas() throws Exception {
        for (String coin : COINS) {
            APIRequestMoeda("https://api.coincap.io/v2/assets/" + coin);
        }
    }

    public void APIRequestMoeda(String url) throws IOException, SQLException {
        URL APIurl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) APIurl.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            Moedavolatil moeda = APIResponseMoeda(connection);
            Moedavolatil existingMoeda = pgMoedavolatilDAO.getMoedavolatil(moeda.getDataRequisicao(), moeda.getSigla());
            if (existingMoeda != null) {
                existingMoeda.setValorusd(moeda.getValorusd());
                existingMoeda.setMarketcap(moeda.getMarketcap());
                pgMoedavolatilDAO.update(existingMoeda);
            } else {
                pgMoedavolatilDAO.create(moeda);
            }
        } else {
            System.out.println("ERROR in request of " + url);
        }
    }

    public Moedavolatil APIResponseMoeda(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        String response = content.toString();

        int idStartIndex = response.indexOf("\"id\":") + 6;
        int idEndIndex = response.indexOf("\",", idStartIndex);
        String id = response.substring(idStartIndex, idEndIndex);

        int rankStartIndex = response.indexOf("\"rank\":") + 8;
        int rankEndIndex = response.indexOf("\",", rankStartIndex);
        String rank = response.substring(rankStartIndex, rankEndIndex);

        int symbolStartIndex = response.indexOf("\"symbol\":") + 10;
        int symbolEndIndex = response.indexOf("\",", symbolStartIndex);
        String symbol = response.substring(symbolStartIndex, symbolEndIndex);

        int nameStartIndex = response.indexOf("\"name\":") + 8;
        int nameEndIndex = response.indexOf("\",", nameStartIndex);
        String name = response.substring(nameStartIndex, nameEndIndex);

        int priceUsdStartIndex = response.indexOf("\"priceUsd\":") + 12;
        int priceUsdEndIndex = response.indexOf("\",", priceUsdStartIndex);
        double priceUsd = Double.parseDouble(response.substring(priceUsdStartIndex, priceUsdEndIndex));

        int marketCapUsdStartIndex = response.indexOf("\"marketCapUsd\":") + 16;
        int marketCapUsdEndIndex = response.indexOf("\",", marketCapUsdStartIndex);
        double marketCapUsd = Double.parseDouble(response.substring(marketCapUsdStartIndex, marketCapUsdEndIndex));

        Moedavolatil moeda = new Moedavolatil();
        moeda.setDataRequisicao(new java.sql.Date(System.currentTimeMillis()));
        moeda.setSigla(symbol);
        moeda.setValorusd(priceUsd);
        moeda.setMarketcap(marketCapUsd);

        return moeda;
    }

    @GetMapping("/listar-moedas")
    public List<Cryptomoeda> getMoedas() throws Exception {
        return pgCryptomoedaDAO.all();
    }

    @GetMapping("/get-moedavolatil-bycrypto")
    public List<Moedavolatil> getMoedasBySigla(@RequestParam String sigla) throws Exception {
        return pgCryptomoedaDAO.getMoedavolatilByCrypto(sigla);
    }

}
