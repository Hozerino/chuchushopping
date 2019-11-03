package ws.service;

import org.apache.jena.JenaRuntime;
import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.engine.QueryEngineFactory;
import org.springframework.stereotype.Component;
import ws.WsApplication;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.Objects;

@Component
public class OntologyService {

    private final String TURTLE_LANGUAGE = "TTL";

    private final String schema = "http://www.semanticweb.org/hozer/ontologies/2019/9/untitled-ontology-2#";
    private final File owl = new File(Objects.requireNonNull(WsApplication.class.getClassLoader().getResource("ws.ttl")).getFile());
    private final Model model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
    private final String sparQLPrefixes = "PREFIX : <http://www.semanticweb.org/hozer/ontologies/2019/9/untitled-ontology-2#> \n" +
            "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
            "PREFIX xml: <http://www.w3.org/XML/1998/namespace> \n" +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n\n";

    @PostConstruct
    private void postConstruct() {
        model.read(owl.getAbsolutePath(), TURTLE_LANGUAGE);
    }

    public String sparql(String query) {

        String fullQuery = sparQLPrefixes + query;
        Query q = QueryFactory.create(fullQuery);
        QueryExecution qe = QueryExecutionFactory.create(q, model);

        ResultSet res = qe.execSelect();

        return toJson(res);
    }

    public List<Individual> getAllIndividualsOfType(String type) {
        Resource clazz = model.getResource(schema + type);
        return ((OntModel) model).listIndividuals().toList();
    }

    private String toJson(ResultSet resultSet) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, resultSet);

        return new String(outputStream.toByteArray());
    }
}
