package co.com.crediya.jpa;

import co.com.crediya.jpa.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JPARepository extends JpaRepository<UserEntity, Long> {

    // Verifica si el correo existe (ya lo tenías)
    boolean existsByCorreoElectronico(String correoElectronico);

    // Busca el usuario por su correo (¡Agrega esta línea!)
    Optional<UserEntity> findByCorreoElectronico(String correoElectronico);

}