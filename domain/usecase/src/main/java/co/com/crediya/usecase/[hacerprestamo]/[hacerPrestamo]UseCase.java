package co.com.crediya.usecase.hacerprestamo;

import co.com.crediya.model.exceptions.BusinessException;
import co.com.crediya.model.exceptions.UserNotFoundException;
import co.com.crediya.model.prestamo.Prestamo;
import co.com.crediya.model.prestamo.gateways.PrestamoRepository;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@RequiredArgsConstructor
public class HacerPrestamoUseCase {

    private static final BigDecimal MONTO_MINIMO = new BigDecimal("500000");
    private static final BigDecimal MONTO_MAXIMO = new BigDecimal("50000000");
    private static final BigDecimal TASA_INTERES_MENSUAL = new BigDecimal("0.015");
    private static final BigDecimal CAPACIDAD_ENDEUDAMIENTO = new BigDecimal("0.30");

    private final UserRepository userRepository;
    private final PrestamoRepository prestamoRepository;

    public Prestamo solicitarPrestamo(String correoElectronico,
                                      BigDecimal monto,
                                      Integer plazoMeses) {
        validarCorreo(correoElectronico);
        validarMonto(monto);
        validarPlazo(plazoMeses);

        User user = userRepository.findByCorreo(correoElectronico);
        if (user == null) {
            throw new UserNotFoundException(
                    "No existe un usuario con el correo: " + correoElectronico);
        }

        BigDecimal cuota = calcularCuota(monto, plazoMeses);
        validarCapacidadEndeudamiento(user, cuota);

        Prestamo prestamo = Prestamo.builder()
                .correoElectronico(user.getCorreoElectronico())
                .monto(monto)
                .plazoMeses(plazoMeses)
                .tasaInteres(TASA_INTERES_MENSUAL)
                .cuotaMensual(cuota)
                .estado("PENDIENTE")
                .fechaSolicitud(LocalDate.now())
                .build();

        return prestamoRepository.save(prestamo);
    }

    private void validarCorreo(String correo) {
        if (correo == null || correo.isBlank()) {
            throw new BusinessException("El correo electrónico es obligatorio");
        }
    }

    private void validarMonto(BigDecimal monto) {
        if (monto == null || monto.compareTo(MONTO_MINIMO) < 0) {
            throw new BusinessException("El monto mínimo es " + MONTO_MINIMO);
        }
        if (monto.compareTo(MONTO_MAXIMO) > 0) {
            throw new BusinessException("El monto máximo es " + MONTO_MAXIMO);
        }
    }

    private void validarPlazo(Integer plazoMeses) {
        if (plazoMeses == null || plazoMeses < 1 || plazoMeses > 60) {
            throw new BusinessException("El plazo debe estar entre 1 y 60 meses");
        }
    }

    private void validarCapacidadEndeudamiento(User user, BigDecimal cuota) {
        BigDecimal maximoPermitido = user.getSalarioBase().multiply(CAPACIDAD_ENDEUDAMIENTO);
        if (cuota.compareTo(maximoPermitido) > 0) {
            throw new BusinessException(
                    "La cuota mensual (" + cuota + ") supera el 30% de su salario base ("
                            + maximoPermitido + ")");
        }
    }

    private BigDecimal calcularCuota(BigDecimal monto, Integer plazoMeses) {
        BigDecimal uno = BigDecimal.ONE;
        BigDecimal factor = uno.add(TASA_INTERES_MENSUAL).pow(plazoMeses);
        return monto.multiply(TASA_INTERES_MENSUAL)
                .divide(uno.subtract(uno.divide(factor, 10, RoundingMode.HALF_UP)),
                        2, RoundingMode.HALF_UP);
    }
}