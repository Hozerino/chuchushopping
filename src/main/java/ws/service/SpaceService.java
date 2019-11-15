package ws.service;

import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.springframework.stereotype.Service;
import ws.helper.OntologyHelper;
import ws.rest.response.SpaceResponse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SpaceService {

    private static Model model = OntologyHelper.model();
    private static String schema = OntologyHelper.defaultSchema();
    private static List<Property> props = OntologyHelper.getSpaceProperties(model, schema);
    private static Individual commecialCenter = OntologyHelper.getCommercialCenterIndividual();
    private static List<SpaceResponse> shoppingMap = null;

    public List<SpaceResponse> getAllStores() {
        List<SpaceResponse> res = new ArrayList<>();

        // contorno tecnico de baixo custo pra cachear, evita ficar rodando 1001 vezes
        if(shoppingMap == null) {
            buildOntology(commecialCenter, props, new HashSet<>(), res);
            shoppingMap = res;
        }

        return shoppingMap;

    }

    private void buildOntology(Resource resource, List<Property> props, Set<Resource> alreadyVisited, List<SpaceResponse> spaces) {
        List<Resource> neighbours = new ArrayList<>();

        String type = OntologyHelper.getSpaceType(resource);
        SpaceResponse spr = new SpaceResponse(resource.getLocalName(), type);

        if (!alreadyVisited.contains(resource)) {
            alreadyVisited.add(resource);
            props.forEach(prop -> {
                if (resource.hasProperty(prop)) {
                    Resource connection = resource.getProperty(prop).getResource();

                    spr.setSpaceProperties(prop.getLocalName(), connection.getLocalName());

                    neighbours.add(connection);
                }
            });

            spaces.add(spr);
        }

        neighbours.forEach(res -> buildOntology(res.asResource(), props, alreadyVisited, spaces));
    }
}
