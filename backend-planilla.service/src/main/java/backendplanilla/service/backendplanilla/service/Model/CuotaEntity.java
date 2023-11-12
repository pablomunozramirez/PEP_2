package backendplanilla.service.backendplanilla.service.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CuotaEntity {
    private int id_cuota;
    private int numero_cuota;
    private int monto;
    private int montoDescuentoP;
    private int montoDescuentoI;
    private String pagada;
    private String rutCuota;
    private LocalDate fechaCuota; // Fecha que se creó la cuota
    private LocalDate fechaDePago; // Fecha que se paga la cuota.
}
