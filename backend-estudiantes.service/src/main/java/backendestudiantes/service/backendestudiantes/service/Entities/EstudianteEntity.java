package backendestudiantes.service.backendestudiantes.service.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name="estudiantes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteEntity {
    @Id
    @Column(unique = true, nullable = false)
    private String rut;

    private String nombre;
    private String apellidos;
    private LocalDate fecha_nacimiento;
    private String tipo_colegio;
    private String nombre_colegio;
    private int anio_egreso;
}
