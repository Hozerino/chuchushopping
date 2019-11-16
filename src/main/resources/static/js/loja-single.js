window.onload = function() {
    buildHtmlInfo();
};


function buildHtmlInfo() {
    const urlParams = new URLSearchParams(window.location.search);
    const storeLabel = urlParams.get('loja');

    buildTitle(storeLabel);
    buildProductsList(storeLabel);
}

function buildTitle(storeLabel) {
    $('#store-label').text('Loja: ' + storeLabel);

}
function buildProductsList(storeLabel) {

    var storeInfo = getStoreInfo(storeLabel);
    let products = storeInfo.produtos;

    var arrayLength = products.length;
    for (var i = 0; i < arrayLength; i++) {
        var html = [
        '<div class="col-lg-4 col-md-6 mb-4">\n',
          '<div class="card h-100">\n',
            '<a href="#"><img class="card-img-top" src="http://placehold.it/700x400" alt=""></a>\n',
            '<div class="card-body">\n',
              '<h4 class="card-title">\n',
                '<a href="#">' + products[i].name + '</a>\n',
              '</h4>\n',
              '<h5>$'+ products[i].price +'</h5>\n',
            '</div>\n',
            '<div class="card-footer">\n',
              '<small class="text-muted">&#9733; &#9733; &#9733; &#9733; &#9734;</small>\n',
            '</div>\n',
          '</div>\n',
        '</div>\n']


        $('#store-products').append(html.join(''));
    }
}