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
        validarCamposObligatorios(user);
        validarFormatoCorreo(user.getCorreoElectronico());
        validarSalario(user.getSalarioBase());

        if (userRepository.existsByCorreo(user.getCorreoElectronico())) {
            throw new UserAlreadyExistsException(
                    "El correo electrónico ya se encuentra registrado: " + user.getCorreoElectronico());
        }

        return userRepository.save(user);
    }

    private void validarCamposObligatorios(User user) {
        // Cambia getNombres() por getNombre()
        if (user.getNombre() == null || user.getNombre().isBlank())
            throw new BusinessException("El campo 'nombre' es obligatorio.");

        // Cambia getApellidos() por getApellido()
        if (user.getApellido() == null || user.getApellido().isBlank())
            throw new BusinessException("El campo 'apellido' es obligatorio.");

        if (user.getCorreoElectronico() == null || user.getCorreoElectronico().isBlank())
            throw new BusinessException("El campo 'correo_electronico' es obligatorio.");

        if (user.getSalarioBase() == null)
            throw new BusinessException("El campo 'salario_base' es obligatorio.");
    }

    private void validarFormatoCorreo(String correo) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!correo.matches(regex))
            throw new BusinessException("El formato del correo electrónico no es válido.");
    }

    private void validarSalario(BigDecimal salario) {
        BigDecimal maximo = new BigDecimal("15000000");
        if (salario.compareTo(BigDecimal.ZERO) <= 0)
            throw new BusinessException("El salario base debe ser mayor que cero.");
        if (salario.compareTo(maximo) > 0)
            throw new BusinessException("El salario base no puede superar los 15,000,000.");
    }
}