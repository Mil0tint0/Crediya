package co.com.crediya.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String nombre;
    private String apellido;

    // ¡Asegúrate de que este campo esté aquí!
    private String correoElectronico;

    private String mensaje;
}