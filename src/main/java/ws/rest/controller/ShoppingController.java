package ws.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ws.domain.space.SpaceService;
import ws.domain.user.UserService;
import ws.infrastructure.OntologyUtil;
import ws.rest.request.LoginRequest;
import ws.rest.response.PathResponse;
import ws.rest.response.SpaceResponse;
import ws.domain.user.User;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ShoppingController {

    private final SpaceService spaceService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public ShoppingController(SpaceService spaceService, UserService userService, ObjectMapper objectMapper) {
        this.spaceService = spaceService;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/sparql")
    public String sparqlQuery(@RequestParam String query) {
        return OntologyUtil.sparql(query);
    }

    @GetMapping("/lojas")
    public String getStores() {
        String response = OntologyUtil.sparql("SELECT ?loja WHERE {[a :Store; rdfs:label ?loja]}");
        return response;
    }

    @GetMapping("/users/{cpf}")
    public User getUserByCPF(@PathVariable String cpf) {
        return userService.getUserByCPF(cpf).get();
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }

    @GetMapping("/recommended-stores/{cpf}")
    public ResponseEntity<List<String>> getRecommendedStoresForUser(@PathVariable String cpf) {
        return ResponseEntity.ok(userService.getRecommendedStoresForUser(cpf));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() throws JsonProcessingException {
        return ResponseEntity.ok(OntologyUtil.getCategories());
    }

    @GetMapping("/shortest-paths/{store}")
    public ResponseEntity<List<PathResponse>> getShortestPath(@PathVariable String store) {
        return ResponseEntity.of(Optional.of(spaceService.getShortestPath(null, store)));
    }

    @GetMapping("/estrutura")
    public ResponseEntity<List<SpaceResponse>> getStructure() {
        return ResponseEntity.of(Optional.of(spaceService.getAllStores()));
    }

    @GetMapping("/loja/{label}")
    public String getStoreByLabel(@PathVariable("label") String label) {
        // ia fazer o bagulho pra evitar SparQL injection
        // https://morelab.deusto.es/code_injection/files/sparql_injection.pdf
        // mas a gente ta liberando endpoint de sparql entao dane-se

        return OntologyUtil.sparql(String.format("SELECT ?telefone ?website\n" +
                "WHERE {\n" +
                "    [a :Store;\n" +
                "        rdfs:label \"%s\";\n" +
                "        :website ?website;\n" +
                "        :telephone ?telefone]\n" +
                "}", label.trim()));
    }

    // fica meio pt/en pq o front fica mais bonito se usar os endpoint BR,
    // mas no cod vai nas gringa senao o cabeca chora
    @GetMapping("/produtos")
    public String productsSoldBy(@RequestParam(required = false, value = "loja") String storeLabel) {
        if(StringUtils.isNotBlank(storeLabel)) {
            return OntologyUtil.sparql(String.format(
                    "SELECT ?productLabel ?price ?category\n" +
                            "WHERE {\n" +
                            "    [a :Store ;\n" +
                            "        rdfs:label \"%s\" ;\n" +
                            "        :sells [a :Product;\n" +
                            "        rdfs:label ?productLabel ;\n" +
                            "        :price ?price ;\n" +
                            "        :hasCategory [a :Category ; rdfs:label ?category]]]\n" +
                            "}", storeLabel.trim()
            ));
        }
        return OntologyUtil.sparql("SELECT ?productLabel ?price ?category\n" +
                "WHERE {\n" +
                "\t[a :Product ;\n" +
                "\t\trdfs:label ?productLabel ;\n" +
                "\t\t:price ?price;\n" +
                "\t\t:hasCategory [a :Category ; rdfs:label ?category]]\n" +
                "}");
    }
}
