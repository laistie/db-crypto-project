$(document).ready(function(){
    let isContentVisible = false;
    let currentContent = '';
    $(".btn").click(function() {
        $(".btn").removeClass('btn-active');
        $(this).addClass('btn-active');
    });
    $("#listarMercadosBtn").click(function(){
        if (isContentVisible && currentContent === 'mercados') {
            $("#contentContainer").html('');
            isContentVisible = false;
        } else {
            $.get("/listar-mercados", function(data){
                let content = '<h2>Mercados</h2><table class="data-table"><tr><th class="data-table-content">Nome</th><th class="data-table-content">Rank</th><th class="data-table-content">Pares Troca</th></tr>';
                for (let i = 0; i < data.length; i++) {
                    content += '<tr><td class="data-table-content">' + data[i].nome + '</td><td class="data-table-content">' + data[i].rank + '</td><td class="data-table-content">' + data[i].paresTroca + '</td></tr>';
                }
                content += '</table>';
                $("#contentContainer").html(content);
            });
            isContentVisible = true;
            currentContent = 'mercados';
        }
    });
    $("#listarMoedasBtn").click(function(){
        if (isContentVisible && currentContent === 'moedas') {
            $("#contentContainer").html('');
            isContentVisible = false;
        } else {
            let content = '<h2>Criptomoedas</h2><select name="moeda" id="moeda">' +
                '<option selected disabled>Escolha uma moeda</option>';
            $.get("/listar-moedas", function(data) {
                for (let i = 0; i < data.length; i++) {
                    content += '<option value="' + data[i].sigla + '">' + data[i].nome + '</option>';
                }
                content += '</select><div><canvas id="chart"></canvas></div>';
                $("#contentContainer").html(content);
                isContentVisible = true;
                currentContent = 'moedas';
            });
        }
    });
    var myChart;
    $(document).on('change', "#moeda", function() {
        var selectedSigla = $(this).val();
        $.get("/get-moedavolatil-bycrypto?sigla=" + selectedSigla, function(data) {
            var ctx = document.getElementById('chart').getContext('2d');
            var labels = [];
            var valorusd = [];
            var marketcap = [];
            data.forEach(function(e) {
                labels.push(e.dataRequisicao); // Assuming 'dataRequisicao' is the label
                valorusd.push(e.valorusd); // Assuming 'valorusd' is one of the data points
                marketcap.push(e.marketcap); // Assuming 'marketcap' is one of the data points
            });
            Chart.register({
                id: 'moment', // ID of the adapter
                beforeInit: function(chart, args, options) {
                    chart.options.scales.x.time.parser = moment;
                }
            });
            if (myChart) {
                myChart.destroy();
            }
            myChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: labels, // This should be your dataRequisicao values
                    datasets: [{
                        label: 'Valor USD',
                        data: valorusd, // This should be your valorusd values
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgb(20, 126, 178)',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        x: {
                            type: 'time',
                            time: {
                                unit: 'day' // Or 'month', 'year', etc., depending on your data
                            }
                        },
                        y: {
                            beginAtZero: true,
                        }
                    }
                }
            });
        });
    })
    $("#requestsAPIBtn").click(function() {
        if (isContentVisible && currentContent === 'dadosAPI') {
            $("#contentContainer").html('');
            isContentVisible = false;
        } else {
            let content = '<h2>Leitura de dados dos mercados da API</h2>';
            content += '<button id="requestMercadosAPIBtn" class="btn">Request Mercados API</button>';
            content += '<button id="requestMoedasAPIBtn" class="btn">Request Moedas API</button>';
            $("#contentContainer").html(content);
            isContentVisible = true;
            currentContent = 'dadosAPI';
        }
    });
    $(document).on('click', '#requestMercadosAPIBtn', function() {
        $.get("/API-request-mercados", function() {
            alert('Request Mercados API concluído');
        });
    });
    $(document).on('click', '#requestMoedasAPIBtn', function() {
        $.get("/API-request-moedas", function() {
            alert('Request Moedas API concluído');
        });
    });
});
