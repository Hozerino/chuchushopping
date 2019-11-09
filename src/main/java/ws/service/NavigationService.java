package ws.service;

import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.springframework.stereotype.Service;
import ws.helper.OntologyHelper;
import ws.model.Space;
import ws.rest.response.SpaceResponse;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NavigationService {
    private static final String TURTLE_LANGUAGE = "TTL";

    List<Space> wholeMap = new ArrayList<>();

    @PostConstruct
    private void postConstruct() {
        Model model = OntologyHelper.model();
        String schema = OntologyHelper.defaultSchema();

        List<Property> sides = OntologyHelper.getProperties(model, schema);

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
                    System.out.println(resource.getLocalName() + " Is " + side.getLocalName() + " " + otherSide.getLocalName());
                }
            });
        }

        neighbours.forEach(res -> checkAllConnectionsStartingFromResource(res, sides, alreadyVisited));
    }

    private List<Space> buildWholeMap(List<Property> props, Individual startingPoint) {
        return null;
    }

    // vai receber a URI de uma loja e vai achar o caminho mais curto ate qualquer chao daquela loja
    public List<Individual> pathTo(Individual store) {
        // soh tem um, se n achar eh pq o mapa ta um lixo
        Individual startingPoint = OntologyHelper.getAllIndividualsOfType("CommercialCenter").get(0);
        // TODO vai que eh tua, cabecao
        return null;
    }
}
