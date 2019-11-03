package ws.helper;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import ws.WsApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.Objects;

public class OntologyHelper {

    private static final String TURTLE_LANGUAGE = "TTL";

    private static final String schema = "http://www.semanticweb.org/hozer/ontologies/2019/9/untitled-ontology-2#";
    private static final File owl = new File(Objects.requireNonNull(WsApplication.class.getClassLoader().getResource("ws.ttl")).getFile());
    private static final Model model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
    private static final String sparQLPrefixes = "PREFIX : <http://www.semanticweb.org/hozer/ontologies/2019/9/untitled-ontology-2#> \n" +
            "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
            "PREFIX xml: <http://www.w3.org/XML/1998/namespace> \n" +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n\n";

    static {
        model.read(owl.getAbsolutePath(), TURTLE_LANGUAGE);
    }

    public static String sparql(String query) {
        String fullQuery = sparQLPrefixes + query;
        Query q = QueryFactory.create(fullQuery);
        QueryExecution qe = QueryExecutionFactory.create(q, model);

        ResultSet res = qe.execSelect();

        return toJson(res);
    }

    public static List<Individual> getAllIndividualsOfType(String type) {
        Resource clazz = model.getResource(schema + type);
        return ((OntModel) model).listIndividuals(clazz).toList();
    }

    public static Property getProperty(String property) {
        return model.getProperty(schema + property);
    }

    public static String defaultSchema() {
        return schema;
    }

    private static String toJson(ResultSet resultSet) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, resultSet);

        return new String(outputStream.toByteArray());
    }

    // Straight to the trash
//    public static Model sparqlModel(String query) {
//        ResultSet res = getResultSetFromQuery(query);
//
//        return res.getResourceModel();
//    }
//
//    private static ResultSet getResultSetFromQuery(String query) {
//        String fullQuery = sparQLPrefixes + query;
//        Query q = QueryFactory.create(fullQuery);
//        QueryExecution qe = QueryExecutionFactory.create(q, model);
//
//        return qe.execSelect();
//    }
}
