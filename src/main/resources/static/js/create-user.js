window.onload = function () {
  buildHtmlInfo();
};


function buildHtmlInfo() {
  buildDropdownCategories();
}

function buildDropdownCategories() {
  const categories = getCategories();
  var dropdown = $('#categories')

  categories.forEach(cat => {
    $('#categories').append(
      $('<option></option>').val(cat).text(cat)
    );
  })
}

function newUser() {
  var user = {
    name: null,
    cpf: null,
    cellphone: null,
    likes: [],
    password: null
  }

  user.name = $('#name').val();
  user.cellphone = $("#phone").val();
  user.cpf = $("#cpf").val();
  user.likes = $("#categories").val();
  user.password = $("#password").val();

  createUser(user);
  location.href = 'user-stores.html?cpf=' + user.cpf;
}