package co.com.poli.model.security.gateway;

import co.com.poli.model.security.LogInDTO;
import co.com.poli.model.security.SignUpDTO;
import co.com.poli.model.security.TokenDTO;
import co.com.poli.model.security.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> signUp(SignUpDTO dto);
    Mono<TokenDTO> login(LogInDTO dto);
}
