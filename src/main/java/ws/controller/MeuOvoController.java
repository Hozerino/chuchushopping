package ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ws.service.OntologyService;

@RestController
public class MeuOvoController {

    @Autowired
    OntologyService ontologyService;

    @GetMapping("/sparql")
    public String sparqlQuery(@RequestParam String query) {
        return ontologyService.sparql(query);
    }
}
