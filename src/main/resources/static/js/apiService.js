$.ajaxSetup({
    async: false
});
$.support.cors = true;


function getLojas() {

    let lojas = [];

    $.getJSON('http://localhost:8080/api/lojas', function (data) {

        // pra cada resultado do sparql
        $.each(data.results.bindings, function (index, linha) {
            if (data.results.bindings.length == 0) {
                alert("Nenhuma loja encontrada.");
            }

            //            var stringParams = window.location.href;
            //            var queryParam = new URLSearchParams(stringParams.search());
            //
            //            queryParam.append("loja", linha.loja.value);
            //
            //            let redirectToNewLoja = "loja-single.html?" + queryParam.toString();

            lojas.push(linha.loja.value);

            // pega a div de id="divlojas" e appenda o titulo da loja e o link pra url dela no sistema
            //            $('#divlojas').append(
            //                $('<h1></h1>').text(linha.loja.value),
            //                $('<a></a>').attr('href', redirectToNewLoja).text('Clique aqui!')
            //            );
        });
    });
    return lojas;
}

function getSpaces() {
    let spaces = [];

    $.getJSON('http://localhost:8080/api/estrutura', function (data) {
        $.each(data, function (index, spaceResponse) {
            let space = {
                name: spaceResponse.name,
                topOf: spaceResponse.top_of,
                bottomOf: spaceResponse.bottom_of,
                leftOf: spaceResponse.left_of,
                rightOf: spaceResponse.right_of,
                connects: spaceResponse.connects,
                type: spaceResponse.type,
                floor: spaceResponse.floor,
                storeLabel: spaceResponse.store
            };
            spaces.push(space)
        });
    });

    return spaces;
}

function buildProperSpaces() {
    let stringBasedSpaces = getSpaces();
    let spaces = [];

    console.log(stringBasedSpaces);

    stringBasedSpaces.forEach(function (space, index) {
        let newSpace = {
            name: space.name,
            topOf: stringBasedSpaces.find(element => element.name === space.topOf),
            bottomOf: stringBasedSpaces.find(element => element.name === space.bottomOf),
            leftOf: stringBasedSpaces.find(element => element.name === space.leftOf),
            rightOf: stringBasedSpaces.find(element => element.name === space.rightOf),
            connects: stringBasedSpaces.find(element => element.name === space.connects),
            type: space.type,
            floor: space.floor,
            storeLabel: space.storeLabel
        };
        spaces.push(newSpace);
    });
    console.log(spaces);
    return spaces;
}

$.extend({
    jpost: function(url, body) {
        return $.ajax({
            type: 'POST',
            url: url,
            data: JSON.stringify(body),
            contentType: "application/json",
            dataType: 'json'
        });
    }
});

function getCategories() {
    let categories = [];

    $.getJSON("http://localhost:8080/api/categories", function (data) {
        $.each(data, function (index, category) {
            categories.push(category);
        })
    });

    return categories;
}

function createUser(user) {
    $.jpost('http://localhost:8080/api/users', user).then(res => {
        // TODO tratar erros
        console.log(res);
    });
}

function login(loginInfo) {
    $.jpost('http://localhost:8080/api/login', loginInfo).then(res => {
        // TODO tratar erros
        console.log(res);
    });
}

function getRecommendedStores(CPF) {
    let recommendations = [];
    $.getJSON('http://localhost:8080/api/recommended-stores/' + CPF, function (data) {
        $.each(data, function (index, rec) {
            recommendations.push(rec)
        });
    });
    return recommendations;
}

function getStoreInfo(label) {
    var loja = {
        nome: label,
        telefone: null,
        website: null,
        produtos: []
    }

    // Monta informacoes
    $.getJSON('http://localhost:8080/api/loja/' + label, function (data) {
        if (data.results.bindings.length == 0) {
            alert("Erro na consulta");
        }

        // soh tem uma loja entao pega o 0
        let storeInfo = data.results.bindings[0];

        console.log(storeInfo);
        loja.telefone = storeInfo['telefone'].value;
        loja.website = storeInfo['website'].value;

        //        $('#info').append(
        //            $('<p></p>').text('Telefone: ' + telephone),
        //            $('<a></a>').attr('href', website).text('Website')
        //        )
    });

    // Monta produtos
    $.getJSON('http://localhost:8080/api/produtos?loja=' + label, function (data) {
        if (data.results.bindings.length == 0) {
            alert("Essa loja nao vende nenhum produto.");
        }

        let products = data.results.bindings;

        $.each(products, function (index, prod) {
            var name = prod.productLabel.value;
            var price = prod.price.value;
            var category = prod.category.value;
            var product = {
                name: name,
                price: price,
                category: category
            }

            loja.produtos.push(product);
            //            $('#products').append(
            //                $('<h3></h3>').text(name),
            //                $('<p></p>').text('Por apenas ' + price),
            //                $('<p></p>').text('Categoria: ' + category)
            //            )
        });
    })

    return loja;
}

function getProducts() {

    let products = [];

    $.getJSON('http://localhost:8080/api/produtos', function (data) {

        // pra cada resultado do sparql
        $.each(data.results.bindings, function (index, linha) {
            if (data.results.bindings.length === 0) {
                alert("Nenhum produto encontrado.");
            }

            var product = {
                name: linha.productLabel.value,
                price: linha.price.value,
                category: linha.category.value
            }

            products.push(product);

        });
    });
    return products;
}

function getPathToStore(store) {

    let path = [];

    $.getJSON(`http://localhost:8080/api/shortest-paths/${store}`, function (data) {
        path.push(data)
    });

    return path;
}
