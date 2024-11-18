package co.com.poli.dynamodb.parking;

import co.com.poli.dynamodb.helper.TemplateAdapterOperations;
import co.com.poli.dynamodb.user.mapper.UserEntityMapper;
import co.com.poli.model.carinfo.CarInfo;
import co.com.poli.model.carinfo.gateways.CarInfoRepository;
import co.com.poli.model.security.TokenDTO;
import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;


@Repository
public class ParkingAdapter extends TemplateAdapterOperations<CarInfo, String, ParkingEntity> implements CarInfoRepository {

    private static Logger logger = LoggerFactory.getLogger(ParkingAdapter.class);

    public ParkingAdapter(DynamoDbEnhancedAsyncClient connectionFactory, ObjectMapper mapper) {
        super(connectionFactory, mapper, d -> mapper.map(d, CarInfo.class), "parcking");
    }

    public Mono<List<CarInfo>> getEntityBySomeKeys(String partitionKey, String sortKey) {
        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey, sortKey);
        return query(queryExpression);
    }

    public Mono<List<CarInfo>> getEntityBySomeKeysByIndex(String partitionKey, String sortKey) {
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
    public Mono<Boolean> updateDataCar(CarInfo carInfo) {
        return saveCar(carInfo);
    }

    @Override
    public Flux<CarInfo> getCars() {
        return scan().flatMapMany(Flux::fromIterable);
    }

    @Override
    public Mono<CarInfo> getCar(String idPark, String registration) {
        return query(buildQueryRequest(idPark, registration))
                .map(res -> res.get(0))
                .flatMap(Mono::just)
                .switchIfEmpty(Mono.error(new Throwable("Registro no existe")));
    }

    private QueryEnhancedRequest buildQueryRequest(String idPark, String registration) {
        return QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(k -> k.partitionValue(idPark).sortValue(registration)))
                .build();
    }


    private Mono<Boolean> saveCar(CarInfo carInfo) {
        return save(carInfo)
                .doOnError(err -> logger.info("error {}", err.getMessage()))
                .doOnSuccess(res -> logger.info("Success  {}", res))
                .flatMap(res -> Mono.just(Boolean.TRUE));

    }
}
