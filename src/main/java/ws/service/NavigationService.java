package ws.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.apache.jena.assembler.Mode;
import org.apache.jena.assembler.assemblers.ReasonerFactoryAssembler;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdf.model.impl.PropertyImpl;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerFactory;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.rulesys.RDFSRuleReasonerFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.ReasonerVocabulary;
import org.springframework.stereotype.Service;
import ws.WsApplication;
import ws.helper.OntologyHelper;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;

@Service
public class NavigationService {
    private static final String TURTLE_LANGUAGE = "TTL";

    private static final String schema = "http://www.semanticweb.org/hozer/ontologies/2019/9/untitled-ontology-2#";
    private static final File owl = new File(Objects.requireNonNull(WsApplication.class.getClassLoader().getResource("ws.ttl")).getFile());
    private static final Model model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RDFS_INF);
    private static final String sparQLPrefixes = "PREFIX : <http://www.semanticweb.org/hozer/ontologies/2019/9/untitled-ontology-2#> \n" +
            "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
            "PREFIX xml: <http://www.w3.org/XML/1998/namespace> \n" +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n\n";


    List<Individual> ground;

    @PostConstruct
    private void postConstruct() {
        FileManager.get().readModel(model, owl.getAbsolutePath());
        Resource config = model.createResource();
        config.addProperty(ReasonerVocabulary.PROPruleMode, "backward");
        Reasoner r = RDFSRuleReasonerFactory.theInstance().create(config);

        OntModel inf = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);

        Property topOf = inf.getProperty(schema + "topOf");
        Property bottomOf = inf.getProperty(schema + "bottomOf");
        Property leftOf = inf.getProperty(schema + "leftOf");
        Property rightOf = inf.getProperty(schema + "rightOf");
        Property connects = inf.getProperty(schema + "connects");

        List<Property> sides = Arrays.asList(topOf, bottomOf, leftOf, rightOf, connects);

        Individual commercialCenter = OntologyHelper.getAllIndividualsOfType("CommercialCenter").get(0);

        // Pra funfar, tem q mostrar rightOf Parede1-2 mas nao sei como fazer, talvez com InfModel
//        sides.forEach(side -> {
//            if (commercialCenter.hasProperty(side)) {
//                Statement property = commercialCenter.getProperty(side);
//                Resource otherSide = property.getResource();
//                System.out.println("Is " + side.getLocalName() + otherSide.getLocalName());
//            }
//        });
        checkAllConnectionsStartingFromResource(commercialCenter, sides);


        // montar a matrix vai ser pica, se eh q vai ser feito assim
    }

    private void checkAllConnectionsStartingFromResource(Resource resource, List<Property> sides) {
        List<Resource> resources = new ArrayList<>();
        sides.forEach(side -> {
            if (resource.hasProperty(side)) {
                Statement property = resource.getProperty(side);
                Resource otherSide = property.getResource();
                resources.add(otherSide);
                System.out.println(resource.getLocalName() +" Is " + side.getLocalName() + " " + otherSide.getLocalName());
            }
        });
        resources.forEach(res -> checkAllConnectionsStartingFromResource(res, sides));
    }

    // vai receber a URI de uma loja e vai achar o caminho mais curto ate qualquer chao daquela loja
    public List<Individual> pathTo(Individual store) {
        // soh tem um, se n achar eh pq o mapa ta um lixo
        Individual startingPoint = OntologyHelper.getAllIndividualsOfType("CommercialCenter").get(0);
        // TODO vai que eh tua, cabecao
        return null;
    }
}
