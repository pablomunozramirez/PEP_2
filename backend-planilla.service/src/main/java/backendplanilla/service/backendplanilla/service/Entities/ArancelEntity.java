package backendplanilla.service.backendplanilla.service.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name= "resumen")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArancelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int idResumen;

    private String rutEstudiante;
    private int numeroExamenesRendidos;
    private double promedioExamenes;
    private double montoTotalArancel;
    private String tipoPago;
    private int numeroTotalCuotas;
    private double montoTotalPagado;
    private LocalDate fechaUltimoPago;
    private double saldoPorPagar;
    private int numeroCuotasConRetraso;
}
