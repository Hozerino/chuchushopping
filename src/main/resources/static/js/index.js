window.onload = function() {
    buildHtmlInfo();
};


function buildHtmlInfo() {
    buildStoreList();
    buildProductsList();
}


function buildProductsList() {
    let products = getProducts();

    var arrayLength = products.length;
    for (var i = 0; i < arrayLength; i++) {
        var imagePath = 'img/'+ products[i].name.split(" ").join("_") + '.jpg';
        var html = [
        '<div class="col-lg-4 col-md-6 mb-4">\n',
          '<div class="card h-100">\n',
            '<a href="#"><img class="card-img-top" src="' + imagePath + '" height=253 width=144 alt=""></a>\n',
            '<div class="card-body">\n',
              '<h4 class="card-title">\n',
                '<a href="#">' + products[i].name + '</a>\n',
              '</h4>\n',
              '<h5>R$'+ products[i].price +'</h5>\n',
              '<h5>Categoria: '+ products[i].category+'</h5>\n',
            '</div>\n',
            '<div class="card-footer">\n',
              '<small class="text-muted">&#9733; &#9733; &#9733; &#9733; &#9734;</small>\n',
            '</div>\n',
          '</div>\n',
        '</div>\n']


        $('#general-products').append(html.join(''));
    }
}


function buildStoreList() {
// TODO redirecionar pra pag da loja
    let lojas = getLojas();
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