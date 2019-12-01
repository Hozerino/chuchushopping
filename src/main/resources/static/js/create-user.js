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

  name = $('#name').val();
  cellphone = $("#phone").val();
  cpf = $("#cpf").val();
  likes = $("#categories").val();
  password = $("#password").val();

  if (!(name && cellphone && cpf && password)) {
    alert("Preencha todos os campos (com exceção das categorias, que é opcional)!")
  } else {

    createUser(user);
    location.href = 'user-stores.html?cpf=' + user.cpf;
  }
}