package co.com.crediya.jpa.Mapper;

import co.com.crediya.jpa.Entity.RoleEntity;

public class RoleMapper {

    private RoleMapper() {}

    public static RoleEntity toEntity(Long id, String nombre) {
        return RoleEntity.builder().id(id).nombre(nombre).build();
    }
}