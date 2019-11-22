package ws.domain;

import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.springframework.stereotype.Service;
import ws.infrastructure.OntologyUtil;
import ws.rest.response.PathResponse;
import ws.rest.response.SpaceResponse;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class SpaceService {

    private static Model model = OntologyUtil.model();
    private static String schema = OntologyUtil.defaultSchema();
    private static List<Property> props = OntologyUtil.getSpaceProperties(model, schema);
    private static Individual commercialCenterIndividual = OntologyUtil.getCommercialCenterIndividual();
    private static List<SpaceResponse> shoppingMap = null;
    private Space commercialCenterSpace = new Space(commercialCenterIndividual.getLocalName(), OntologyUtil.getSpaceType(commercialCenterIndividual));

    @PostConstruct
    private void init() {
        List<SpaceResponse> spaceResponseList = new ArrayList<>();
        List<Space> spaceList = new ArrayList<>();

        buildOntology(commercialCenterIndividual, props, new HashSet<>(), spaceResponseList);
        shoppingMap = spaceResponseList;

        buildGraph(commercialCenterIndividual, props, new HashSet<>(), spaceList, commercialCenterSpace);
    }

    public List<SpaceResponse> getAllStores() {
        return shoppingMap;
    }

    private void buildOntology(Resource resource, List<Property> props, Set<Resource> alreadyVisited, List<SpaceResponse> spaces) {
        List<Resource> neighbours = new ArrayList<>();

        String type = OntologyUtil.getSpaceType(resource);
        SpaceResponse spr = new SpaceResponse(resource.getLocalName(), type);

        if (!alreadyVisited.contains(resource)) {
            alreadyVisited.add(resource);
            props.forEach(prop -> {
                if (resource.hasProperty(prop)) {
                    String propName;
                    if (prop.getLocalName().equals("floor")) {
                        propName = resource.getProperty(prop).getString();
                    } else {
                        Resource connection = resource.getProperty(prop).getResource();
                        propName = resource.getProperty(prop).getResource().getLocalName();
                        neighbours.add(connection);
                    }

                    spr.setSpaceProperties(prop.getLocalName(), propName);

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
                    if (prop.getLocalName().equals("floor")) {
                        space.setFloor(resource.getProperty(prop).getString());
                    } else {
                        Resource connection = resource.getProperty(prop).getResource();

                        Space neighbor = hasAlreadyBeenCreated(connection.getLocalName(), spaces);

                        if (neighbor == null) {
                            neighbor = new Space(connection.getLocalName(), OntologyUtil.getSpaceType(connection));
                            buildGraph(connection, props, alreadyVisited, spaces, neighbor);
                        }

                        space.setSpaceProperties(prop.getLocalName(), neighbor);
                    }

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

    public List<PathResponse> getShortestPath(Space startSpace, String storeToBeFound) {
        if (startSpace == null) {
            startSpace = commercialCenterSpace;
        }

        boolean shortestPathFound = false;
        Queue<Space> queue = new LinkedList<>();
        Set<Space> visitedSpaces = new HashSet<>();
        List<Space> shortestPath = new ArrayList<>();
        Map<Space, Space> parentSpaces = new HashMap<>();

        queue.add(startSpace);
        shortestPath.add(startSpace);

        while (!queue.isEmpty()) {
            Space nextSpace = queue.peek();

            if (nextSpace.getBelongsTo() != null) {
                shortestPathFound = isDesiredSpace(storeToBeFound, nextSpace);

                if (shortestPathFound) {
                    shortestPath = transverseMapToGetPath(nextSpace, parentSpaces);
                }
            }

            visitedSpaces.add(nextSpace);
            Space unvisitedSpace = getUnvisitedSpace(nextSpace.getNeighbors(), visitedSpaces);

            if (unvisitedSpace != null) {
                queue.add(unvisitedSpace);
                visitedSpaces.add(unvisitedSpace);
                parentSpaces.put(unvisitedSpace, nextSpace);

                if (unvisitedSpace.getBelongsTo() != null) {
                    shortestPathFound = isDesiredSpace(storeToBeFound, unvisitedSpace);

                    if (shortestPathFound) {
                        shortestPath = transverseMapToGetPath(unvisitedSpace, parentSpaces);
                    }
                }
            } else {
                queue.poll();
            }
            
            if (shortestPathFound) {
                break;
            }
        }

        return PathResponse.convertSpaceListToPathList(shortestPath);
    }

    private boolean isDesiredSpace(String storeToBeFound, Space space) {
        return space.getBelongsTo().equalsIgnoreCase(storeToBeFound);
    }

    private Space getUnvisitedSpace(List<Space> spaces, Set<Space> visitedSpaces) {
        for (Space space : spaces) {
            if (!visitedSpaces.contains(space) && space.isWalkable()) {
                return space;
            }
        }
        return null;
    }

    private List<Space> transverseMapToGetPath(Space spaceToBeFound, Map<Space, Space> parentSpaces) {
        List<Space> shortestPath = new ArrayList<>();
        Space node = spaceToBeFound;

        while (node != null) {
            shortestPath.add(node);
            node = parentSpaces.get(node);
        }

        Collections.reverse(shortestPath);

        if (shortestPath.get(shortestPath.size() - 1).getBelongsTo().equals(shortestPath.get(shortestPath.size() - 2).getBelongsTo())) {
            shortestPath.remove(shortestPath.size() - 1);
        }

        return shortestPath;
    }
}
