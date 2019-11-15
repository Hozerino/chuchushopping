package ws.service;

import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.springframework.stereotype.Service;
import ws.helper.OntologyHelper;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NavigationService {


    @PostConstruct
    private void postConstruct() {
        Model model = OntologyHelper.model();
        String schema = OntologyHelper.defaultSchema();

        List<Property> sides = OntologyHelper.getSpaceProperties(model, schema);

        Individual commercialCenter = OntologyHelper.getAllIndividualsOfType("CommercialCenter", model).get(0);

        checkAllConnectionsStartingFromResource(commercialCenter, sides, new HashSet<>());

        // montar a matrix vai ser pica, se eh q vai ser feito assim
    }

    //TODO acho q da pra deletar esse metodo
    private void checkAllConnectionsStartingFromResource(Resource resource, List<Property> sides, Set<Resource> alreadyVisited) {
        List<Resource> neighbours = new ArrayList<>();
        if (!alreadyVisited.contains(resource)) {
            alreadyVisited.add(resource);
            sides.forEach(side -> {
                if (resource.hasProperty(side)) {
                    Statement property = resource.getProperty(side);
                    Resource otherSide = property.getResource();
                    neighbours.add(otherSide);

                    String type = OntologyHelper.getSpaceType(resource);
                    System.out.println(resource.getLocalName() + "(" + type + ")" + " Is " + side.getLocalName() + " " + otherSide.getLocalName());
                }
            });
        }

        neighbours.forEach(res -> checkAllConnectionsStartingFromResource(res, sides, alreadyVisited));
    }
}
