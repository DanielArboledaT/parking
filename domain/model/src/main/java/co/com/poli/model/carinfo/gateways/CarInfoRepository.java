package co.com.poli.model.carinfo.gateways;

import co.com.poli.model.carinfo.CarInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CarInfoRepository {

    Mono<Boolean> updateDataCar(CarInfo carInfo);

    Flux<CarInfo> getCars();

    Mono<CarInfo> getCar(String idPark, String registration);
}
