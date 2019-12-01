window.onload = function () {
    buildHtmlInfo();
};


function buildHtmlInfo() {
    const urlParams = new URLSearchParams(window.location.search);
    const cpf = urlParams.get('cpf');

    var storeInfo = buildStoreList(cpf);
}

function buildStoreList(cpf) {
    // TODO redirecionar pra pag da loja
    let lojas = getRecommendedStores(cpf);
    if (lojas.length == 0) {
        $('#lista-lojas').append(
            $('<h2></h2>').text("Que pena, não encontramos nenhuma loja para você. :("),
            $('<p></p>').text("Ou este CPF não está cadastrado...")
        )
    }
    console.log(lojas);

    // pega a div de id="divlojas" e appenda o titulo da loja e o link pra url dela no sistema
    var arrayLength = lojas.length;
    for (var i = 0; i < arrayLength; i++) {
        //        var stringParams = window.location.href;
        //        var queryParam = new URLSearchParams(stringParams.search());
        var redirectToNewLoja = "loja-single.html?loja=" + lojas[i];

        $('#lista-lojas').append(
            $('<a></a>').addClass("list-group-item").attr('href', redirectToNewLoja).text(lojas[i])
        );
    }
}