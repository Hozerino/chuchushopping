$(jQuery(document).ready(function($) {
    getStoreInfo();
}));


function getStoreInfo() {
    $.getJSON('http://localhost:8080/api/loja/', function(data) {

        // settar id=title e id=h1-loja

        // pra cada resultado do sparql
        $.each(data.results.bindings, function(index, linha) {

            // pega a div de id="divlojas" e appenda o titulo da loja e o link pra url dela no sistema
            $('#divlojas').append(
                $('<h1></h1>').text(linha.loja.value),
                $('<a></a>').attr('href', 'http://localhost:8080/loja/'+linha.loja.value).text('Clique aqui!')
            );
        });
    });
}