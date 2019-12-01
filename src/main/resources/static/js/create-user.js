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

  if (isEmpty(user.name) || isEmpty(user.cellphone) || isEmpty(user.cpf) || isEmpty(user.password)) {
    alert("Preencha todos os campos (com exceção das categorias, que é opcional)!")
  } else {

    createUser(user);
    location.href = 'user-stores.html?cpf=' + user.cpf;
  }
}

function isEmpty(o) {
  return Object.keys(o).every(function (x) {
    return o[x] === '' || o[x] === null;  // or just "return o[x];" for falsy values
  });
}