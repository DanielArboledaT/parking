package co.com.poli.api;

import co.com.poli.api.mapper.ParkingMapper;
import co.com.poli.api.request.ParkingRequest;
import co.com.poli.api.response.ResponseDto;
import co.com.poli.model.carinfo.CarInfo;
import co.com.poli.model.security.User;
import co.com.poli.usecase.parking.ParkingUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Handler {

    private final ParkingUseCase parkingUseCase;

    @PreAuthorize("hasAnyAuthority('Employee', 'Admin')")
    public Mono<ServerResponse> park(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ParkingRequest.class)
                .flatMap(request -> {
                    CarInfo carInfo = ParkingMapper.INSTANCE.RequestToModel(request);
                    return parkingUseCase.parkCar(carInfo)
                            .flatMap(response -> buildSuccessResponse(null, "Registro creado correctamente", HttpStatus.CREATED));
                });
    }

    @PreAuthorize("hasAnyAuthority('Employee', 'Admin')")
    public Mono<ServerResponse> removeCar(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ParkingRequest.class)
                .flatMap(request -> {
                    CarInfo carInfo = ParkingMapper.INSTANCE.RequestToModel(request);
                    return parkingUseCase.removeCar(carInfo)
                            .flatMap(response -> buildSuccessResponse(null, "Registro eliminado exitosamente", HttpStatus.OK));
                });
    }

    @PreAuthorize("hasAnyAuthority('Employee', 'Admin')")
    public Mono<ServerResponse> getCar(ServerRequest serverRequest) {

        String idpark = serverRequest.pathVariable("idpark");
        String registration = serverRequest.pathVariable("registration");
        return parkingUseCase.getCar(idpark, registration)
                .flatMap(carInfo -> buildSuccessResponse(carInfo, "Registro obtenido exitosamente", HttpStatus.OK));
    }

    @PreAuthorize("hasAuthority('Admin')")
    public Mono<ServerResponse> listCars(ServerRequest serverRequest) {
        return parkingUseCase.listCars()
                .collectList()
                .flatMap(list -> buildSuccessFluxResponse(list, "Lista de carros", HttpStatus.OK));
    }

    private Mono<ServerResponse> buildSuccessResponse(CarInfo carInfo, String message, HttpStatus httpStatus) {
        return ServerResponse
                .ok()
                .bodyValue(ResponseDto
                        .builder()
                        .status(httpStatus)
                        .message(message)
                        .data(carInfo)
                        .build());
    }

    private Mono<ServerResponse> buildSuccessFluxResponse(List<CarInfo> listCars, String message, HttpStatus httpStatus) {
        return ServerResponse
                .ok()
                .bodyValue(ResponseDto
                        .builder()
                        .status(httpStatus)
                        .message(message)
                        .data(listCars)
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
