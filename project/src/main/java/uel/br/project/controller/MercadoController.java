package uel.br.project.controller;

import uel.br.project.dao.PgMercadoDAO;
import uel.br.project.model.Mercado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import static java.lang.Integer.parseInt;

@RestController
public class MercadoController {
    @Autowired
    private PgMercadoDAO pgMercadoDAO;

    private static final String API_URL_BINANCE = "https://api.coincap.io/v2/exchanges/binance";
    private static final String API_URL_KUCOIN = "https://api.coincap.io/v2/exchanges/kucoin";
    private static final String API_URL_KRAKEN = "https://api.coincap.io/v2/exchanges/kraken";

    @GetMapping("/API-request-mercados")
    public void APIRequestMercados() throws Exception {
        APIRequestMercado(API_URL_BINANCE);
        APIRequestMercado(API_URL_KUCOIN);
        APIRequestMercado(API_URL_KRAKEN);
    }

    public void APIRequestMercado(String url) throws IOException, SQLException {
        URL APIurl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) APIurl.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            Mercado market = APIResponseMercado(connection);
            Mercado marketExistent = pgMercadoDAO.getMercadoByNome(market.getNome());
            if (marketExistent != null) {
                marketExistent.setRank(market.getRank());
                marketExistent.setParesTroca(market.getParesTroca());
                pgMercadoDAO.update(marketExistent);
            } else{
                pgMercadoDAO.create(market);
            }
        } else {
            System.out.println("ERROR in request of " + url);
        }
    }

        public Mercado APIResponseMercado(HttpURLConnection connection) throws IOException {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            connection.disconnect();

            String response = content.toString();

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
                Mercado mercado = new Mercado();
                mercado.setNome(marketName);
                mercado.setRank(marketRank);
                mercado.setParesTroca(marketTradingPairs);
                return mercado;
            } else {
                throw new RuntimeException("Failed to extract market name from response");
            }
        }

    @GetMapping("/listar-mercados")
    public List<Mercado> listarMercados() throws SQLException {
        return pgMercadoDAO.all();
    }
}
