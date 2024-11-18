package co.com.poli.dynamodb.user.mapper;

import co.com.poli.dynamodb.user.UserEntity;
import co.com.poli.model.security.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserEntityMapper {

    UserEntityMapper INSTANCE = Mappers.getMapper( UserEntityMapper.class );
    UserEntity modelToEntity(User user);
    User entityToModel(UserEntity userEntity);

}
