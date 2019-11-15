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
        var html = [
        '<div class="col-lg-4 col-md-6 mb-4">',
          '<div class="card h-100">',
            '<a href="#"><img class="card-img-top" src="http://placehold.it/700x400" alt=""></a>',
            '<div class="card-body">',
              '<h4 class="card-title">',
                '<a href="#">' + products[i].name + '</a>',
              '</h4>',
              '<h5>'+ products[i].price +'</h5>',
            '</div>',
            '<div class="card-footer">',
              '<small class="text-muted">&#9733; &#9733; &#9733; &#9733; &#9734;</small>',
            '</div>',
          '</div>',
        '</div>',
        '']


        $('#general-products').append(html);
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
//        var redirectToNewLoja = "loja-single.html?" + queryParam.toString();

        $('#lista-lojas').append(
            $('<a></a>').addClass("list-group-item").attr('href', "www.google.com").text(lojas[i])
        );
    }
}