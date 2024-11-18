package co.com.poli.usecase.login;

import co.com.poli.model.security.LogInDTO;
import co.com.poli.model.security.TokenDTO;
import co.com.poli.model.security.gateway.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LogInUseCase {
    private final UserRepository userRepository;

    public Mono<TokenDTO> login(LogInDTO dto) {
        return userRepository.login(dto);
    }
}
