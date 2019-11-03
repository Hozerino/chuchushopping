window.onload = function() {
    var paramsString = this.window.location.href;
    var searchParams = new URLSearchParams(paramsString);

    label = searchParams.get('loja');

    getStoreInfo(label)
};

function getStoreInfo(label) {
    $('#h1-loja').text('Bem vindo à loja ' + label);
    $('#title').text(label);


    // Monta informacoes
    $.getJSON('http://localhost:8080/api/loja/' + label, function(data) {
        if(data.results.bindings.length == 0) {
            alert("Erro na consulta");
        }

        // soh tem uma loja entao pega o 0
        let storeInfo = data.results.bindings[0];

        console.log(storeInfo);
        var telephone = storeInfo['telefone'].value;
        var website = storeInfo['website'].value;

        $('#info').append(
            $('<p></p>').text('Telefone: ' + telephone),
            $('<a></a>').attr('href', website).text('Website')
        )
    });

    // Monta produtos
    $.getJSON('http://localhost:8080/api/produtos?loja='+label, function(data) {
        if(data.results.bindings.length == 0) {
            alert("Essa loja nao vende nenhum produto.");
        }

        let products = data.results.bindings;

        $.each(products, function(index, prod) {
            var name = prod.productLabel.value;
            var price = prod.price.value;
            var category = prod.category.value;

            $('#products').append(
                $('<h3></h3>').text(name),
                $('<p></p>').text('Por apenas ' + price),
                $('<p></p>').text('Categoria: ' + category)
            )
        })
    })
}