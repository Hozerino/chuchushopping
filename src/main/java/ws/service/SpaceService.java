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
    private static List<Property> props = OntologyHelper.getProperties(model, schema);
    private static Individual commecialCenter = OntologyHelper.getCommercialCenterIndividual();

    public List<SpaceResponse> getAllStores() {
        List<SpaceResponse> res = new ArrayList<>();

        buildOnthology(commecialCenter, props, new HashSet<>(), res);

        return res;
    }

    private void buildOnthology(Resource resource, List<Property> props, Set<Resource> alreadyVisited, List<SpaceResponse> spaces) {
        List<Resource> neighbours = new ArrayList<>();

        SpaceResponse spr = new SpaceResponse(resource.getLocalName());

        if (!alreadyVisited.contains(resource)) {
            alreadyVisited.add(resource);
            props.forEach(prop -> {
                if (resource.hasProperty(prop)) {
                    Resource connection = resource.getProperty(prop).getResource();

                    spr.setSpaceProps(prop.getLocalName(), connection.getLocalName());

                    neighbours.add(connection);
                }
            });

            spaces.add(spr);
        }

        neighbours.forEach(res -> buildOnthology(res, props, alreadyVisited, spaces));
    }

    //TODO fazer isso aqui funfa
    private String getResourceType(Resource resource) {
        return ((Individual) resource.asResource()).getOntClass(true).getLocalName();
    }
}
