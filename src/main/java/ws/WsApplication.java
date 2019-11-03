package ws;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.impl.PropertyImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.Objects;

@SpringBootApplication
public class WsApplication {

	public static final String TURTLE_LANGUAGE = "TTL";
	static String schema = "http://www.semanticweb.org/hozer/ontologies/2019/9/untitled-ontology-2#";
	static File owl = new File(Objects.requireNonNull(WsApplication.class.getClassLoader().getResource("ws.ttl")).getFile());

	public static void main(String[] args) {

		Model model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		model.read(owl.getAbsolutePath(), TURTLE_LANGUAGE);

		Individual brennao = ((OntModel) model).getIndividual(schema + "Brenno_Cremonini");
		Individual space = ((OntModel) model).getIndividual(schema + "Corredor1-1");

		Property p = new PropertyImpl(schema + "bottomOf");
		space.getProperty(p);
//		SpringApplication.run(WsApplication.class, args);
	}

}
