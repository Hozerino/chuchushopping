package ws.domain.user;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface UserRepository {
    User save(User user);
    Optional<User> getUserByCPF(String CPF);
    List<String> getRecommendedStoresForUser(String CPF);
}
