package co.com.crediya.usecase.register;

import co.com.crediya.model.exceptions.BusinessException;
import co.com.crediya.model.exceptions.UserAlreadyExistsException;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class RegisterUseCase {

    private final UserRepository userRepository;

    public User register(User user) {
        validar(user);

        if (userRepository.existsByCorreo(user.getCorreoElectronico())) {
            throw new UserAlreadyExistsException(
                    "Ya existe un usuario con el correo: " + user.getCorreoElectronico());
        }
        return userRepository.save(user);
    }

    private void validar(User user) {
        if (user == null) {
            throw new BusinessException("El usuario no puede ser nulo");
        }
        if (user.getNombres() == null || user.getNombres().isBlank()) {
            throw new BusinessException("El nombre es obligatorio");
        }
        if (user.getApellidos() == null || user.getApellidos().isBlank()) {
            throw new BusinessException("El apellido es obligatorio");
        }
        if (user.getCorreoElectronico() == null || user.getCorreoElectronico().isBlank()) {
            throw new BusinessException("El correo electrónico es obligatorio");
        }
        if (user.getFechaNacimiento() == null) {
            throw new BusinessException("La fecha de nacimiento es obligatoria");
        }
        if (user.getSalarioBase() == null || user.getSalarioBase().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("El salario base debe ser mayor que cero");
        }
    }
}