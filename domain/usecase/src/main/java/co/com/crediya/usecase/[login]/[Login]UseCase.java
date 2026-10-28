package co.com.crediya.usecase.login;

import co.com.crediya.model.exceptions.BusinessException;
import co.com.crediya.model.exceptions.UserNotFoundException;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginUseCase {

    private final UserRepository userRepository;

    public User login(String correoElectronico) {
        if (correoElectronico == null || correoElectronico.isBlank()) {
            throw new BusinessException("El correo electrónico es obligatorio");
        }
        User user = userRepository.findByCorreo(correoElectronico);
        if (user == null) {
            throw new UserNotFoundException(
                    "No existe un usuario con el correo: " + correoElectronico);
        }
        return user;
    }
}