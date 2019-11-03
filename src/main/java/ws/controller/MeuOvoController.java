package ws.controller;

import org.springframework.web.bind.annotation.*;
import ws.helper.OntologyHelper;

@RestController
@RequestMapping("/api")
public class MeuOvoController {

    @GetMapping("/sparql")
    public String sparqlQuery(@RequestParam String query) {
        return OntologyHelper.sparql(query);
    }

    @GetMapping("/lojas")
    public String getStores() {
        // ia fazer pegar os individuals do tipo loja mas fica zuado, vou deixar o front cuidar dessa parte
//        return OntologyHelper.getAllIndividualsOfType("Store").stream()
//                .map(Store::build).collect(Collectors.toList());

        return OntologyHelper.sparql("SELECT ?loja WHERE {[a :Store; rdfs:label ?loja]}");
    }

    @GetMapping("/loja/{label}")
    public String getStoreByLabel(@PathVariable("label") String label) {
        // ia fazer o bagulho pra evitar SparQL injection
        // https://morelab.deusto.es/code_injection/files/sparql_injection.pdf
        // mas a gente ta liberando endpoint de sparql entao dane-se

        return OntologyHelper.sparql(String.format("SELECT ?nome ?telefone ?website ?produtos\n" +
                "WHERE {\n" +
                "    [a :Store;\n" +
                "        rdfs:label \"%s\";\n" +
                "        :website ?website;\n" +
                "        :telephone ?telefone]\n" +
                "}", label));
    }

    // fica meio pt/en pq o front fica mais bonito se usar os endpoint BR,
    // mas no cod vai nas gringa senao o cabeca chora
    @GetMapping("/produtos")
    public String productsSoldBy(@RequestParam(required = true, value = "loja") String storeLabel) {
        return OntologyHelper.sparql(String.format(
                "SELECT ?productLabel ?price ?category\n" +
                        "WHERE {\n" +
                        "    [a :Store ;\n" +
                        "        rdfs:label \"%s\" ;\n" +
                        "        :sells [a :Product;\n" +
                        "        rdfs:label ?productLabel ;\n" +
                        "        :price ?price ;\n" +
                        "        :hasCategory [a :Category ; rdfs:label ?category]]]\n" +
                        "}", storeLabel
        ));
    }
}
