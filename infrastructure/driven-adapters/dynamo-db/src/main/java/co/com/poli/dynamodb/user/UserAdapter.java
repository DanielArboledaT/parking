package co.com.poli.dynamodb.user;

import co.com.poli.dynamodb.helper.TemplateAdapterOperations;
import co.com.poli.dynamodb.user.mapper.UserEntityMapper;
import co.com.poli.jwt.provider.JwtProvider;
import co.com.poli.model.carinfo.CarInfo;
import co.com.poli.model.security.LogInDTO;
import co.com.poli.model.security.SignUpDTO;
import co.com.poli.model.security.TokenDTO;
import co.com.poli.model.security.User;
import co.com.poli.model.security.gateway.UserRepository;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;
import java.util.UUID;

@Repository
public class UserAdapter  extends TemplateAdapterOperations<User, String, UserEntity> implements UserRepository {

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public UserAdapter(DynamoDbEnhancedAsyncClient connectionFactory,
                       ObjectMapper mapper,
                       PasswordEncoder passwordEncoder,
                       JwtProvider jwtProvider) {
        super(connectionFactory, mapper, d -> mapper.map(d, User.class), "users");
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<List<User>> getEntityBySomeKeys(String partitionKey, String sortKey) {
        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey, sortKey);
        return query(queryExpression);
    }

    public Mono<List<User>> getEntityBySomeKeysByIndex(String partitionKey, String sortKey) {
        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey, sortKey);
        return queryByIndex(queryExpression, "secondary_index" /*index is optional if you define in constructor*/);
    }

    private QueryEnhancedRequest generateQueryExpression(String partitionKey, String sortKey) {
        return QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(Key.builder().partitionValue(partitionKey).build()))
                .queryConditional(QueryConditional.sortGreaterThanOrEqualTo(Key.builder().sortValue(sortKey).build()))
                .build();
    }

    @Override
    public Mono<User> signUp(SignUpDTO dto) {
        UserEntity user = getUserDocument(dto);
        Mono<List<User>> userExists = query(buildQueryRequest(user.getEmail()));
        return userExists
                .flatMap(res -> res.isEmpty() ?
                        save(UserEntityMapper.INSTANCE.entityToModel(user)) :
                        Mono.error(new Throwable("email already in use")));

    }

    @Override
    public Mono<TokenDTO> login(LogInDTO dto) {
        return query(buildQueryRequest(dto.email()))
                .map(res -> res.get(0))
                .filter(userDocument -> passwordEncoder.matches(dto.password(), userDocument.getPassword()))
                .map(userDocument -> new TokenDTO(jwtProvider.generateToken(UserEntityMapper.INSTANCE.modelToEntity(userDocument))))
                .switchIfEmpty(Mono.error(new Throwable("bad credentials")))
                .onErrorResume(e -> Mono.error(new Throwable("bad credentials")));
    }

    private QueryEnhancedRequest buildQueryRequest(String email) {
        return QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(k -> k.partitionValue(email)))
                .build();
    }

    private UserEntity getUserDocument(SignUpDTO dto) {
        UserEntity user = new UserEntity();
        user.setUuid(UUID.randomUUID().toString());
        user.setName(dto.name());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setStatus(Boolean.TRUE);
        user.setRoles(dto.role());
        return user;
    }
}
