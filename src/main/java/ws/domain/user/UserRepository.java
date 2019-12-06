package ws.domain.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public interface UserRepository {
    User save(User user);
    Optional<User> getUserByCPF(String CPF);
    List<String> getRecommendedStoresForUser(String CPF);
}
