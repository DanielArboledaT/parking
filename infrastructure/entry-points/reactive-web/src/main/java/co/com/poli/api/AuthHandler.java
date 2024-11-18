package co.com.poli.api;

import co.com.poli.api.response.ResponseDto;
import co.com.poli.model.security.LogInDTO;
import co.com.poli.model.security.SignUpDTO;
import co.com.poli.model.security.TokenDTO;
import co.com.poli.model.security.User;
import co.com.poli.usecase.login.LogInUseCase;
import co.com.poli.usecase.signup.SignUpUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AuthHandler {
    private final SignUpUseCase signUpUseCase;
    private final LogInUseCase logInUseCase;

    public AuthHandler(SignUpUseCase signUpUseCase, LogInUseCase logInUseCase) {
        this.signUpUseCase = signUpUseCase;
        this.logInUseCase = logInUseCase;
    }

    public Mono<ServerResponse> signUp(ServerRequest request) {

        return request.bodyToMono(SignUpDTO.class)
                .flatMap(signUpUseCase::signUp)
                .flatMap(this::buildSuccessResponse)
                .onErrorResume(this::buildErrorResponse);

    }

    public Mono<ServerResponse> logIn(ServerRequest request) {

        /*return request.bodyToMono(LogInDTO.class)
                .flatMap(dto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(logInUseCase.login(dto), TokenDTO.class))
                .onErrorResume(this::buildErrorResponse);*/
        return request.bodyToMono(LogInDTO.class)
                .flatMap(logInUseCase::login)
                .flatMap(this::buildSuccessResponseLogin)
                .onErrorResume(this::buildErrorResponse);
    }

    private Mono<ServerResponse> buildSuccessResponse(User user) {
        return ServerResponse
                .ok()
                .bodyValue(ResponseDto
                        .builder()
                        .status(HttpStatus.OK)
                        .message("Usuario creado exitosamente")
                        .data(User
                                .builder()
                                .uuid(user.getUuid())
                                .build())
                        .build());
    }

    private Mono<ServerResponse> buildSuccessResponseLogin(TokenDTO tokenDTO) {
        return ServerResponse
                .ok()
                .bodyValue(ResponseDto
                        .builder()
                        .status(HttpStatus.OK)
                        .message("Usuario logueado exitosamente")
                        .data(tokenDTO.token())
                        .build());
    }

    private Mono<ServerResponse> buildErrorResponse(Throwable e) {
        return ServerResponse
                .status(500)
                .bodyValue(ResponseDto
                        .builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message(e.getMessage())
                        .build());
    }
}
