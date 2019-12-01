package ws.domain.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ws.exception.InvalidPasswordException;
import ws.exception.UserAlreadyExistsException;
import ws.exception.UserNotFoundException;
import ws.infrastructure.OntologyUtil;
import ws.rest.controller.request.LoginRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        if (!getUserByCPF(user.getCPF()).isPresent()) {
            return userRepository.save(user);
        } else {
            throw new UserAlreadyExistsException("Usuário já cadastrado");
        }
    }

    public User loginUser(LoginRequest loginRequest) {
        Optional<User> optionalUser = getUserByCPF(loginRequest.getCPF());
        return optionalUser.map(user -> {
            if (user.getPassword().equals(loginRequest.getPassword())) {
                return user;
            } else {
                throw new InvalidPasswordException("Senha inválida");
            }
        }).orElseThrow(() -> new UserNotFoundException("Usuario não encontrado"));
    }

    private Optional<User> getUserByCPF(String CPF) {
        return userRepository.getUserByCPF(CPF);
    }


    public List<String> getRecommendedStoresForUser(String CPF) {
        return getUserByCPF(CPF).map(user -> {
            List<String> queryResults = new ArrayList<>();
            user.getLikes().forEach(taste -> queryResults.add(OntologyUtil.sparql(String.format("SELECT ?name\n" +
                    "WHERE {\n" +
                    "    [a :Store;\n" +
                    "        rdfs:label ?name;\n" +
                    "        :sells [a :Product; :hasCategory [a :Category ; rdfs:label \"%s\"]]]" +
                    "}", taste))));

            List<String> recommendations = new ArrayList<>();

            queryResults.forEach(res -> {
                try {
                    JsonNode node = objectMapper.readTree(res);
                    node.get("results").get("bindings").findValues("name").forEach(jsonNode -> recommendations.add(jsonNode.get("value").asText()));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
            return recommendations;
        }).orElseThrow(() -> new UserNotFoundException("Usuario não encontrado"));
    }
}