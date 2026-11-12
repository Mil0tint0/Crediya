package co.com.crediya.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String nombre;     // JSON: "nombre"
    private String apellido;   // JSON: "apellido"
    private String direccion;  // JSON: "direccion"
    private String telefono;   // JSON: "telefono"
    private LocalDate fechaNacimiento;
    private String correoElectronico;
    private BigDecimal salarioBase;
}