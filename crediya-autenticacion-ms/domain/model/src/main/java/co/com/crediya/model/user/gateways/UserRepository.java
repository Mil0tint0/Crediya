package co.com.crediya.model.user.gateways;

import co.com.crediya.model.user.User;

public interface UserRepository {
    boolean existsByCorreo(String correoElectronico);
    User findByCorreo(String correoElectronico); // <-- Agrega esta línea
    User save(User user);
}