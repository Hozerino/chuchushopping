### Como rodar o projeto:
1) Abra o terminal na pasta root (chuchushopping/) e execute:
`$ ./gradlew bootRun`

2) Abra seu navegador e acesse a URL `localhost:8080/` que vocÊ será redirecionado à página index.html

3) Para navegar no ChuChuShopping, você pode usar o menu da NavBar que fica na parte superior direita das páginas

### Pontos de melhoria (não deu tempo de fazer)
- Deixar o ws.ttl como um arquivo servido estaticamente e referenciá-lo na ontologia do user.ttl, que seria utilizada apenas para armazenar usuários;
Como isso não foi feito (e a URI do ws.ttl daria um erro 404), a user.ttl possui todas as triplas que a ws.ttl possui para poder ter os mesmos conceitos de Store, Product etc;
- Replicar a ideia acima para o product.ttl;
- Apesar de estar implementado no BackEnd, o FrontEnd não mostra a quantidade dos produtos em estoque e não possui um incremento/decremento;
- Tudo seria mais simples caso fosse utilizado o SparQL no lugar da API do Jena, porém a ideia deste EP é justamente "explorar" a API e tirar o máximo dela.
- Utilizar PreparedStatments para construir queries SparQL com parâmetros, evitando SparQL injection (https://morelab.deusto.es/code_injection/files/sparql_injection.pdf).