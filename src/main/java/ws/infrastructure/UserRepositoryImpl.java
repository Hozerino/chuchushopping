package ws.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.impl.PropertyImpl;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.OWL2;
import org.apache.jena.vocabulary.RDFS;
import org.springframework.stereotype.Component;
import ws.domain.user.User;
import ws.domain.user.UserRepository;
import ws.exception.UserNotFoundException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {

    private final ObjectMapper objectMapper;

    public UserRepositoryImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public User save(User user) {
        OntModel ontModel = getUserOntModel();

        user.setName(user.getName().replace(" ", "_"));

        List<Property> props = OntologyUtil.getUserProperties();

        OntClass userClass = ontModel.getOntClass(OntologyUtil.schema + "User");
        Individual newUser = ontModel.createIndividual(OntologyUtil.schema + user.getName(), userClass);

        newUser.addRDFType(OWL2.NamedIndividual);
        newUser.addProperty(RDFS.label, user.getName());

        props.forEach(prop -> {
            switch (prop.getLocalName()) {
                case "cpf":
                    newUser.addProperty(prop, user.getCpf());
                    break;
                case "celphone":
                    newUser.addProperty(prop, user.getCellphone());
                    break;
                case "likes":
                    user.getLikes().forEach(like -> {
                        if (like != null) {
                            newUser.addProperty(prop, OntologyUtil.getResourceWithName(like));
                        }
                    });
                    break;
            }
        });

        OntologyUtil.writeData(ontModel);

        return user;
    }

    @Override
    public Optional<User> getUserByCPF(String CPF) {
        List<Individual> individuals = getAllUsers();

        Individual foundIndividual = null;
        for (Individual individual : individuals) {
            String individualCPF = individual.getPropertyValue(new PropertyImpl(OntologyUtil.schema + "cpf")).asLiteral().getString();
            if (individualCPF.equals(CPF)) {
                foundIndividual = individual;
                break;
            }
        }

        if (foundIndividual != null) {
            return Optional.of(convertIndividualToUser(foundIndividual));
        } else {
            return Optional.empty();
        }
    }

    private User convertIndividualToUser(Individual individual) {
        User user = new User();

        user.setName(individual.getLocalName());

        String cpf = individual.getPropertyValue(new PropertyImpl(OntologyUtil.schema + "cpf")).asLiteral().getString();
        user.setCpf(cpf);

        user.setCellphone(individual.getPropertyValue(new PropertyImpl(OntologyUtil.schema + "celphone")).asLiteral().getString());
        user.setLikes(getUserTaste(cpf));

        return user;
    }

    @Override
    public List<String> getRecommendedStoresForUser(String CPF) {
        return getUserByCPF(CPF).map(user -> {
            List<String> queryResults = new ArrayList<>();
            user.getLikes().forEach(taste -> queryResults.add(OntologyUtil.executeSparqlWithSpecificModel(String.format("SELECT ?name\n" +
                    "WHERE {\n" +
                    "    [a :Store;\n" +
                    "        rdfs:label ?name;\n" +
                    "        :sells [a :Product; :hasCategory [a :Category ; rdfs:label \"%s\"]]]" +
                    "}", taste), getUserOntModel())));

            List<String> recommendations = new ArrayList<>();

            queryResults.forEach(res -> {
                try {
                    JsonNode node = objectMapper.readTree(res);
                    node.get("results").get("bindings").findValues("name").forEach(jsonNode -> {
                        String recommendation = jsonNode.get("value").asText();
                        if (!recommendations.contains(recommendation)) {
                            recommendations.add(recommendation);
                        }
                    });
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
            return recommendations;
        }).orElseThrow(() -> new UserNotFoundException("Usuario não encontrado"));
    }

    private List<String> getUserTaste(String cpf) {
        List<String> ans = new ArrayList<>();

        String res = OntologyUtil.executeSparqlWithSpecificModel(String.format("SELECT ?like WHERE {" +
                "[a :User;" +
                ":cpf \"%s\";" +
                ":likes [a :Category;" +
                "rdfs:label ?like]" +
                "]" +
                "}", cpf), getUserOntModel());

        JsonNode node = null;
        try {
            node = objectMapper.readTree(res);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert node != null;
        node.get("results").get("bindings").findValues("like").forEach(like -> ans.add(like.get("value").asText()));

        return ans;
    }

    private OntModel getUserOntModel() {
        InputStream in = FileManager.get().open("src/main/resources/user.ttl");
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        ontModel.read(in, OntologyUtil.schema, "TURTLE");

        return ontModel;
    }

    private List<Individual> getAllUsers() {
        return OntologyUtil.getAllIndividualsOfType("User", getUserOntModel());
    }

}

