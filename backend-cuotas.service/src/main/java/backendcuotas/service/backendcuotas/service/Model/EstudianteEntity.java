package backendcuotas.service.backendcuotas.service.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EstudianteEntity {
    private String rut;
    private String nombre;
    private String apellidos;
    private LocalDate fecha_nacimiento;
    private String tipo_colegio;
    private String nombre_colegio;
    private int anio_egreso;
}
