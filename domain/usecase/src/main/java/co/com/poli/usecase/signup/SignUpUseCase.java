package co.com.poli.usecase.signup;

import co.com.poli.model.security.SignUpDTO;
import co.com.poli.model.security.User;
import co.com.poli.model.security.gateway.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SignUpUseCase {
    private final UserRepository userRepository;

    public Mono<User> signUp(SignUpDTO dto) {
        return userRepository.signUp(dto);
    }
}
