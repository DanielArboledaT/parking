package co.com.poli.api.request;

public record ParkingRequest(
        String idPark,
        String carRegistration,
        String driverName
) {
}
