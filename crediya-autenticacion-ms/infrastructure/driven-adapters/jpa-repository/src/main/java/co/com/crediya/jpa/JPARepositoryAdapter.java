package co.com.crediya.jpa;

import co.com.crediya.jpa.Entity.UserEntity;
import co.com.crediya.jpa.Mapper.UserMapper;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JPARepositoryAdapter implements UserRepository {

    private final JPARepository jpaRepository;

    @Override
    public boolean existsByCorreo(String correoElectronico) {
        // Usamos el nuevo método del repositorio
        return jpaRepository.existsByCorreoElectronico(correoElectronico);
    }

    @Override
    public User findByCorreo(String correoElectronico) {
        // Necesitaremos un método para buscar por correo en el JPARepository
        // (Te lo dejo abajo en el paso 3)
        return jpaRepository.findByCorreoElectronico(correoElectronico)
                .map(UserMapper::toDomain)
                .orElse(null);
    }

    @Override
    @Transactional
    public User save(User user) {
        log.info("Guardando usuario con correo: {}", user.getCorreoElectronico());
        UserEntity entity = UserMapper.toEntity(user);
        UserEntity saved = jpaRepository.save(entity);
        return UserMapper.toDomain(saved);
    }
}