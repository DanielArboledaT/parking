package co.com.poli.dynamodb.parking.mapper;

import co.com.poli.dynamodb.parking.ParkingEntity;
import co.com.poli.model.carinfo.CarInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ParkingEntityMapper {

    ParkingEntityMapper INSTANCE = Mappers.getMapper( ParkingEntityMapper.class );
    ParkingEntity modelToEntity(CarInfo carInfo);

}
