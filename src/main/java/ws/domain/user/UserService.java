package ws.domain.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ws.exception.InvalidPasswordException;
import ws.exception.UserAlreadyExistsException;
import ws.exception.UserNotFoundException;
import ws.rest.request.LoginRequest;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        if (!getUserByCPF(user.getCpf()).isPresent()) {
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

    public Optional<User> getUserByCPF(String CPF) {
        return userRepository.getUserByCPF(CPF);
    }

    public List<String> getRecommendedStoresForUser(String CPF) {
        return userRepository.getRecommendedStoresForUser(CPF);
    }
}