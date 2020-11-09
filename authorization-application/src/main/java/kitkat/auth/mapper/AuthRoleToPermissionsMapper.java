package kitkat.auth.mapper;

import kitkat.auth.model.dto.AuthRoleToPermissionsDto;
import kitkat.auth.model.entity.AuthRoleToPermissions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthRoleToPermissionsMapper implements DtoMapper<AuthRoleToPermissionsDto, AuthRoleToPermissions>,
        EntityMapper<AuthRoleToPermissions, AuthRoleToPermissionsDto> {

    private final ModelMapper modelMapper;

    public AuthRoleToPermissionsMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthRoleToPermissionsDto toDto(AuthRoleToPermissions authRoleToPermissions) {
        return modelMapper.map(authRoleToPermissions, AuthRoleToPermissionsDto.class);
    }

    @Override
    public AuthRoleToPermissions toEntity(AuthRoleToPermissionsDto authRoleToPermissionsDto) {
        return modelMapper.map(authRoleToPermissionsDto, AuthRoleToPermissions.class);
    }
}
