package co.com.poli.dynamodb.parking;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.LocalDate;


@DynamoDbBean
public class ParkingEntity {

    private String idPark;
    private String carRegistration;
    private String status;
    private String driverName;
    private LocalDate datePark;
    private LocalDate dateUpdatedPark;


    @DynamoDbPartitionKey
    public String getIdPark() {
        return idPark;
    }

    @DynamoDbSortKey
    public String getCarRegistration() {
        return carRegistration;
    }

    @DynamoDbAttribute("status")
    public String getStatus() {
        return status;
    }

    @DynamoDbAttribute("datePark")
    public LocalDate getDatePark() {
        return datePark;
    }

    @DynamoDbAttribute("dateUpdatedPark")
    public LocalDate dateUpdatedPark() {
        return dateUpdatedPark;
    }

    @DynamoDbAttribute("driverName")
    public String getDriverName() {
        return driverName;
    }

    public void setIdPark(String idPark) {
        this.idPark = idPark;
    }

    public void setCarRegistration(String carRegistration) {
        this.carRegistration = carRegistration;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setDatePark(LocalDate datePark) {
        this.datePark = datePark;
    }

    public LocalDate getDateUpdatedPark() {
        return dateUpdatedPark;
    }

    public void setDateUpdatedPark(LocalDate dateUpdatedPark) {
        this.dateUpdatedPark = dateUpdatedPark;
    }
}
