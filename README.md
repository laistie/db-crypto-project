# Trabalho-BD

Repositório destinado ao desenvolvimento do projeto de coleta e análise de dados de criptomoedas.

## Proposta do projeto

Este projeto tem como objetivo realizar a coleta de dados de criptomoedas por meio de APIs para posterior tratamento e análise. O foco principal da coleta de dados será na Binance, o maior mercado de transações de criptomoedas atualmente. O projeto visa proporcionar observações valiosas acerca do mercado ao longo do tempo, auxiliando investidores, traders e entusiastas no acompanhamento das tendências e no monitoramento das criptomoedas.

## Desenvolvimento

### Planejamento de Etapas

#### 1. Criação do Banco de Dados PostgreSQL

- Definir o esquema do banco de dados para armazenar informações relacionadas a mercados de criptomoedas, criptomoedas individuais, volatilidade de preços e vendas de criptomoedas.

- Criar tabelas correspondentes às entidades mencionadas acima, garantindo que a estrutura do banco de dados esteja alinhada com os requisitos do projeto.

- Tabelas 
  - Mercado:  representa informações sobre um mercado específico de criptomoedas, como Binance, KuCoin ou Kraken. Inclui dados como nome do mercado, classificação (rank), número de pares de troca e outras métricas relevantes.

  - Criptomoeda: representa informações sobre uma criptomoeda individual, como Bitcoin, Ethereum, etc. Inclui detalhes como nome da criptomoeda, símbolo, preço atual, capitalização de mercado, volume de negociação e mais.

  - Moedavolatil: registra informações sobre a volatilidade dos preços de uma criptomoeda ao longo do tempo. Isso inclui valores em USD, data de requisição, variação percentual e outros indicadores de flutuação de preço.


#### 2. Implementação do Mercado e MercadoService

- Criar a classe `Mercado` que representará informações sobre um mercado de criptomoedas. Mapear esta classe para a tabela correspondente no banco de dados.

- Implementar o `MercadoService`, que será responsável por coletar dados de APIs de mercados de criptomoedas, como Binance, KuCoin e Kraken.

- Realizar operações de tratamento e armazenamento desses dados no banco de dados, assegurando a integridade e consistência dos registros.

#### 3. Implementação da CriptoMoeda e CriptoMoedaService

- Criar a classe `CriptoMoeda` para representar informações sobre criptomoedas individuais, como Bitcoin, Ethereum, etc. Mapear esta classe para a tabela correspondente no banco de dados.

- Implementar o `CriptoMoedaService`, cuja função é coletar dados de APIs relacionadas a criptomoedas.

- Desenvolver uma classe `MoedaVolatil` que representará a volatilidade dos preços das criptomoedas. Mapear esta classe para a tabela correspondente no banco de dados.

- Implementar lógica para analisar e tratar informações como MarketCap, Valor em USD e Data de Requisição para as criptomoedas.

#### 4. Integração e Testes

- Integrar o sistema de coleta de dados com as classes de modelo e serviços criados, garantindo uma interação eficaz e precisa.

- Realizar testes abrangentes para verificar o funcionamento adequado da coleta, armazenamento e tratamento de dados.

### Tecnologias Utilizadas

- JDK: versão 17.0.8
- Spring Boot 3.1.5
  - Maven
  - Spring Boot DevTools
  - Spring Web
  - PostgreSQL Driver
  - Thymeleaf

### APIs Utilizadas

- CoinStats: [Documentação](https://documenter.getpostman.com/view/5734027/RzZ6Hzr3)
- CoinCap: [Documentação](https://docs.coincap.io/)
  - Requests de mercados de criptomoedas
- Binance: [Documentação](https://binance-docs.github.io/apidocs/spot/en/#exchange-information)

## Documentos

### Diagrama Entidade-Relacionamento (DER)

Você pode visualizar o Diagrama Entidade-Relacionamento (DER) do projeto [aqui](https://github.com/laistie/Trabalho-BD/blob/main/docs/DER.png).

### Modelo Relacional (MER)

O Modelo Entidade-Relacional (MER) do projeto está disponível [aqui](https://github.com/laistie/Trabalho-BD/blob/main/docs/MER.png).
