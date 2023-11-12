package backendcuotas.service.backendcuotas.service.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cuota")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuotaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id_cuota;
    private int numero_cuota;
    private int monto;
    private int montoDescuentoP;
    private int montoDescuentoI;
    private String pagada;

    @Column(name = "rut_cuota")
    private String rutCuota;
    @Column(name = "fecha_cuota")
    private LocalDate fechaCuota; // Fecha que se creó la cuota
    private LocalDate fechaDePago; // Fecha que se paga la cuota.
}
