$(jQuery(document).ready(function($) {
    getLojas();
}));


function getLojas() {
    $.getJSON('http://localhost:8080/api/lojas', function(data) {

        // pra cada resultado do sparql
        $.each(data.results.bindings, function(index, linha) {
            if(data.results.bindings.length == 0) {
                alert("Nenhuma loja encontrada.");
            }
            var stringParams = window.location.href;
            var queryParam = new URLSearchParams(stringParams.search());

            queryParam.append("loja", linha.loja.value);

            let redirectToNewLoja = "loja-single.html?" + queryParam.toString();

            // pega a div de id="divlojas" e appenda o titulo da loja e o link pra url dela no sistema
            $('#divlojas').append(
                $('<h1></h1>').text(linha.loja.value),
                $('<a></a>').attr('href', redirectToNewLoja).text('Clique aqui!')
            );
        });
    });
}