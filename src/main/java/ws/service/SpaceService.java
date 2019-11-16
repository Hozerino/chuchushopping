package ws.service;

import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.springframework.stereotype.Service;
import ws.helper.OntologyHelper;
import ws.model.Space;
import ws.rest.response.SpaceResponse;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class SpaceService {

    private static Model model = OntologyHelper.model();
    private static String schema = OntologyHelper.defaultSchema();
    private static List<Property> props = OntologyHelper.getSpaceProperties(model, schema);
    private static Individual commercialCenterIndividual = OntologyHelper.getCommercialCenterIndividual();
    private static List<SpaceResponse> shoppingMap = null;
    private static List<Space> shoppingGraph = null;
    private Space commercialCenterSpace = new Space(commercialCenterIndividual.getLocalName(), OntologyHelper.getSpaceType(commercialCenterIndividual));

    @PostConstruct
    private void init() {
        List<SpaceResponse> spaceResponseList = new ArrayList<>();
        List<Space> spaceList = new ArrayList<>();
        buildOntology(commercialCenterIndividual, props, new HashSet<>(), spaceResponseList);
        buildGraph(commercialCenterIndividual, props, new HashSet<>(), spaceList, commercialCenterSpace);
        shoppingGraph = spaceList;
        shoppingMap = spaceResponseList;
    }

    public List<SpaceResponse> getAllStores() {
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


    private void buildGraph(Resource resource, List<Property> props, Set<Resource> alreadyVisited, List<Space> spaces, Space space) {
        spaces.add(space);

        if (!alreadyVisited.contains(resource)) {
            alreadyVisited.add(resource);

            props.forEach(prop -> {
                if (resource.hasProperty(prop)) {
                    Resource connection = resource.getProperty(prop).getResource();

                    String type = OntologyHelper.getSpaceType(connection);
                    if (type.equalsIgnoreCase("Obstacle")) {
                        type = "Obstacle";
                    } else {
                        type = "Walkable";
                    }

                    Space neighbor = hasAlreadyBeenCreated(connection.getLocalName(), spaces);
                    if (neighbor == null) {
                        neighbor = new Space(connection.getLocalName(), type);
                        buildGraph(connection, props, alreadyVisited, spaces, neighbor);
                    }

                    space.setSpaceProperties(prop.getLocalName(), neighbor);
                }
            });
        }
    }

    private Space hasAlreadyBeenCreated(String label, List<Space> spaces) {
        for (Space space : spaces) {
            if (label.equals(space.getLabel())) {
                return space;
            }
        }

        return null;
    }


    public List<Space> shortestPathBFS(Space startSpace, String storeToBeFound) {
        boolean shortestPathFound;
        Queue<Space> queue = new LinkedList<>();
        Set<Space> visitedSpaces = new HashSet<>();
        List<Space> shortestPath = new ArrayList<>();
        Map<Space, Space> parentSpaces = new HashMap<>();
        queue.add(startSpace);
        shortestPath.add(startSpace);

        while (!queue.isEmpty()) {
            Space nextNode = queue.peek();

            if (nextNode.getBelongsTo() != null) {
                shortestPathFound = nextNode.getBelongsTo().equals(storeToBeFound);
                if (shortestPathFound) {
                    shortestPath = transverseMapToGetPath(nextNode, parentSpaces);
                }
            }

            visitedSpaces.add(nextNode);
            System.out.println(queue);
            Space unvisitedSpace = getUnvisitedSpace(nextNode.getNeighbors(), visitedSpaces);

            if (unvisitedSpace != null) {
                queue.add(unvisitedSpace);
                visitedSpaces.add(unvisitedSpace);
                parentSpaces.put(unvisitedSpace, nextNode);

                if (unvisitedSpace.getBelongsTo() != null) {
                    shortestPathFound = unvisitedSpace.getBelongsTo().equals(storeToBeFound);

                    if (shortestPathFound) {
                        transverseMapToGetPath(unvisitedSpace, parentSpaces);
                    }
                }
            } else {
                queue.poll();
            }
        }

        return shortestPath;
    }

    private Space getUnvisitedSpace(List<Space> spaces, Set<Space> visitedSpaces) {
        for (Space space : spaces) {
            if (!visitedSpaces.contains(space) && space.isWalkable()) {
                return space;
            }
        }
        return null;
    }

    private List<Space> transverseMapToGetPath(Space nodeToBeFound, Map<Space, Space> parentNodes) {
        List<Space> shortestPath = new ArrayList<>();
        Space node = nodeToBeFound;

        while (node != null) {
            shortestPath.add(node);
            node = parentNodes.get(node);
        }

        Collections.reverse(shortestPath);
        return shortestPath;
    }
}
