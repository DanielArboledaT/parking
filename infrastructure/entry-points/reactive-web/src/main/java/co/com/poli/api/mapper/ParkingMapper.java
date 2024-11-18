package co.com.poli.api.mapper;

import co.com.poli.api.request.ParkingRequest;
import co.com.poli.model.carinfo.CarInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ParkingMapper {

    ParkingMapper INSTANCE = Mappers.getMapper( ParkingMapper.class );
    CarInfo RequestToModel(ParkingRequest parkingRequest);

}
