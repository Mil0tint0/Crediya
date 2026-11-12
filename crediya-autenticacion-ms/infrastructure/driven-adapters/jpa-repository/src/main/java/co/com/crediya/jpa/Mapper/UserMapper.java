package co.com.crediya.jpa.Mapper;

import co.com.crediya.jpa.Entity.UserEntity;
import co.com.crediya.model.user.User;

import java.math.BigDecimal;

public class UserMapper {

    private UserMapper() {}

    public static User toDomain(UserEntity entity) {
        if (entity == null) return null;
        return User.builder()
                .nombre(entity.getNombre())
                .apellido(entity.getApellido())
                .fechaNacimiento(entity.getFechaNacimiento())
                .direccion(entity.getDireccion())
                .telefono(entity.getTelefono())
                .correoElectronico(entity.getCorreoElectronico())
                // Convertimos de String (BD) a BigDecimal (Dominio)
                .salarioBase(entity.getSalarioBase() != null ? new BigDecimal(entity.getSalarioBase()) : null)
                .build();
    }

    public static UserEntity toEntity(User domain) {
        if (domain == null) return null;
        return UserEntity.builder()
                .nombre(domain.getNombre())
                .apellido(domain.getApellido())
                .fechaNacimiento(domain.getFechaNacimiento())
                .direccion(domain.getDireccion())
                .telefono(domain.getTelefono())
                .correoElectronico(domain.getCorreoElectronico())
                // Convertimos de BigDecimal (Dominio) a String (BD)
                .salarioBase(domain.getSalarioBase() != null ? domain.getSalarioBase().toString() : null)
                .build();
    }
}