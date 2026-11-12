package co.com.crediya.api;

import co.com.crediya.api.dto.UserRequest;
import co.com.crediya.api.dto.UserResponse;
import co.com.crediya.model.user.User;
import co.com.crediya.usecase.register.RegisterUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final RegisterUseCase registerUseCase;

    @PostMapping
    public ResponseEntity<UserResponse> registrarUsuario(@RequestBody UserRequest request) {
        // El log muestra el correo que viene desde Postman
        log.info("Recibida petición HTTP POST para registrar usuario con correo: {}", request.getCorreoElectronico());

        // Mapeo del DTO al Dominio
        User user = User.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .fechaNacimiento(request.getFechaNacimiento()) // Jackson ya lo mapeó desde "fecha_nacimiento"
                .direccion(request.getDireccion())
                .telefono(request.getTelefono())
                .correoElectronico(request.getCorreoElectronico()) // Jackson ya lo mapeó desde "correo_electronico"
                .salarioBase(request.getSalarioBase()) // Jackson ya lo mapeó desde "salario_base"
                .build();

        User registrado = registerUseCase.register(user);

        // Mapeo del Dominio al DTO de Respuesta
        UserResponse response = UserResponse.builder()
                .nombre(registrado.getNombre())
                .apellido(registrado.getApellido())
                .correoElectronico(registrado.getCorreoElectronico())
                .mensaje("Usuario registrado exitosamente.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}