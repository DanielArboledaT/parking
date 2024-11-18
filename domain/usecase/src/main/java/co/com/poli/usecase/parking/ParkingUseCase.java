package co.com.poli.usecase.parking;

import co.com.poli.model.carinfo.CarInfo;
import co.com.poli.model.carinfo.gateways.CarInfoRepository;
import co.com.poli.model.constants.Constants;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@AllArgsConstructor
public class ParkingUseCase{

    private final CarInfoRepository carInfoRepository;

    public Mono<Boolean> parkCar(CarInfo carInfo) {
        carInfo.setStatus(Constants.CREATED.getValue());
        carInfo.setDatePark(LocalDate.now());
        return carInfoRepository.updateDataCar(carInfo);
    }

    public Mono<Boolean> removeCar(CarInfo carInfo) {
        carInfo.setStatus(Constants.FINISHED.getValue());
        carInfo.setDateUpdatedPark(LocalDate.now());
        return carInfoRepository.updateDataCar(carInfo);
    }

    public Mono<CarInfo> getCar(String idpark, String registration) {
        return carInfoRepository.getCar(idpark, registration);
    }

    public Flux<CarInfo> listCars () {
        return carInfoRepository.getCars();
    }

}
