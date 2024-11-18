package co.com.poli.model.carinfo;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CarInfo {

    private String idPark;
    private String carRegistration;
    private String driverName;
    private LocalDate datePark;
    private LocalDate dateUpdatedPark;
    private String status;


}
