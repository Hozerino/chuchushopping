package ws.domain.user;

import org.springframework.stereotype.Service;
import ws.infrastructure.OntologyUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByCPF(String CPF) {
        return userRepository.getUserByCPF(CPF);
    }

    public List<String> getRecommendedStoresForUser(User user) {
        List<String> queryResults = new ArrayList<>();
        user.getLikes().forEach(taste -> {
            queryResults.add(OntologyUtil.sparql(String.format("SELECT ?name\n" +
                    "WHERE {\n" +
                    "    [a :Store;\n" +
                    "        rdfs:label ?name;\n" +
                    "        :sells [a :Product; :hasCategory [a :Category ; rdfs:label \"%s\"]]]" +
                    "}", taste)));
        });
        return queryResults;
    }
}