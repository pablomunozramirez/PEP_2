package backendplanilla.service.backendplanilla.service.Service;

import backendplanilla.service.backendplanilla.service.Entities.ArancelEntity;
import backendplanilla.service.backendplanilla.service.Entities.PruebaEntity;
import backendplanilla.service.backendplanilla.service.Model.CuotaEntity;
import backendplanilla.service.backendplanilla.service.Model.EstudianteEntity;
import backendplanilla.service.backendplanilla.service.Repository.ArancelRepository;
import backendplanilla.service.backendplanilla.service.Repository.PruebaRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArancelService {
    @Autowired
    PruebaService pruebaService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    PruebaRepository pruebaRepository;
    @Autowired
    ArancelRepository arancelRepository;

    public void aplicarDescuentos() {
        ArrayList<EstudianteEntity> estudiantes = (ArrayList<EstudianteEntity>) findAllEstudiantes();
        for (EstudianteEntity estudiante : estudiantes) {
            String rutEstudiante = estudiante.getRut();
            long numeroCuotas = countByRutCuota(rutEstudiante);
            if (numeroCuotas == 1) {
                int promedioPruebas = pruebaService.calcularPromedioPruebas(rutEstudiante);
                double descuento = calcularDescuento(promedioPruebas);

                ArrayList<CuotaEntity> cuotas = (ArrayList<CuotaEntity>) findByRutCuota(rutEstudiante);
                if (!cuotas.isEmpty()) {
                    CuotaEntity cuota = cuotas.get(0);
                    double monto = cuota.getMonto();
                    monto = monto * descuento;
                    cuota.setMontoDescuentoP((int) monto);
                    save(cuota);
                }
            } else {
                if (existenPruebasDeEstudiante(rutEstudiante)) {
                    calcularDescuentosCuotas(rutEstudiante);
                    calcularInteresCuotas(rutEstudiante);
                } else {
                    calcularInteresCuotas(rutEstudiante);
                }
            }
        }
    }

    public void calcularDescuentosCuotas(String rut){
        ArrayList<PruebaEntity> pruebasEstudiante = pruebaRepository.findByRutPrueba(rut);
        ArrayList<CuotaEntity> cuotasEstudiante = (ArrayList<CuotaEntity>) findByRutCuota(rut);
        for (CuotaEntity cuota : cuotasEstudiante) {
            LocalDate fechaCuota = cuota.getFechaDePago();
            int promedio = 0;
            int total = 0;
            for (PruebaEntity prueba : pruebasEstudiante) {
                if (mismoMesYAnio(fechaCuota, prueba.getFecha_examen())) {
                    promedio = promedio + prueba.getPuntaje();
                    total = total + 1;
                }
            }
            if (total != 0 && promedio != 0) {
                double promedioPruebas = promedio / total;
                double descuento = calcularDescuento((int) promedioPruebas);
                double monto = (double) cuota.getMonto();
                monto = monto * descuento;
                cuota.setMontoDescuentoP((int) monto);
                save(cuota);
            }
        }
    }

    public void calcularInteresCuotas(String rut) {
        ArrayList<CuotaEntity> cuotasEstudiante = (ArrayList<CuotaEntity>) findByRutCuota(rut);
        for (CuotaEntity cuota : cuotasEstudiante) {
            long mesesDeDiferencia = ChronoUnit.MONTHS.between(cuota.getFechaDePago(), LocalDate.now());
            int meses = Math.toIntExact(mesesDeDiferencia);

            double monto = cuota.getMontoDescuentoP();
            double montoFinal;

            if (meses == 1) {
                montoFinal = monto * 1.03;
            } else if (meses == 2) {
                montoFinal = monto * 1.06;
            } else if (meses == 3) {
                montoFinal = monto * 1.09;
            } else if (meses > 3) {
                montoFinal = monto * 1.15;
            } else {
                montoFinal = monto * 1;
            }
            cuota.setMontoDescuentoI((int) montoFinal);
            save(cuota);
        }
    }

    public ArrayList<ArancelEntity> resumenArancel (){
        ArrayList<EstudianteEntity> estudiantes = (ArrayList<EstudianteEntity>) findAllEstudiantes();
        ArrayList <ArancelEntity> resumen = new ArrayList<>();
        for(EstudianteEntity estudiante : estudiantes){
            String rutEstudiante = estudiante.getRut();
            ArancelEntity resumenEstudiante = generarResumenEstudiante(rutEstudiante);
            resumen.add(resumenEstudiante);
        }
        return  resumen;
    }

    public ArancelEntity generarResumenEstudiante (String rut){
        ArancelEntity resumen;
        List<CuotaEntity> cuotas = findByRutCuota(rut);
        int numero_cuotas = cuotas.size();
        if (arancelRepository.existsByRutEstudiante(rut)) {
            resumen = arancelRepository.findByRutEstudiante(rut);
            resumen.setRutEstudiante(rut);
            resumen.setNumeroExamenesRendidos(numeroExamenesrendidos(rut));
            resumen.setPromedioExamenes(pruebaService.calcularPromedioPruebas(rut));
            resumen.setMontoTotalArancel(montoTotalArancel(cuotas));
            resumen.setTipoPago(tipoPago(cuotas));
            resumen.setNumeroTotalCuotas(numero_cuotas);
            resumen.setMontoTotalPagado(montoTotalPagado(cuotas));
            resumen.setFechaUltimoPago(fechaUltimoPago(cuotas));
            resumen.setSaldoPorPagar(saldoPorPagar(cuotas));
            resumen.setNumeroCuotasConRetraso(cuotaConRetraso(cuotas));
        }else {
            resumen = new ArancelEntity();
            resumen.setRutEstudiante(rut);
            resumen.setNumeroExamenesRendidos(numeroExamenesrendidos(rut));
            resumen.setPromedioExamenes(pruebaService.calcularPromedioPruebas(rut));
            resumen.setMontoTotalArancel(montoTotalArancel(cuotas));
            resumen.setTipoPago(tipoPago(cuotas));
            resumen.setNumeroTotalCuotas(numero_cuotas);
            resumen.setMontoTotalPagado(montoTotalPagado(cuotas));
            resumen.setFechaUltimoPago(fechaUltimoPago(cuotas));
            resumen.setSaldoPorPagar(saldoPorPagar(cuotas));
            resumen.setNumeroCuotasConRetraso(cuotaConRetraso(cuotas));
        }
        arancelRepository.save(resumen);
        return resumen;
    }

    public int numeroExamenesrendidos(String rut){
        long a = pruebaRepository.countByRutPrueba(rut);
        int b = (int)a;
        return b;
    }

    public String tipoPago(List<CuotaEntity> cuotas){
        int n = cuotas.size();
        String resultado = "Cuotas";
        if (n==1){
            for(CuotaEntity cuota : cuotas){
                if (cuota.getMonto() == 750000) {
                    resultado = "Contado";
                    break;
                }
            }
        }
        return resultado;
    }

    public double montoTotalArancel(List<CuotaEntity> cuotas){
        double arancelTotal=0;
        for(CuotaEntity cuota : cuotas){
            arancelTotal = arancelTotal + cuota.getMontoDescuentoI();
        }
        return arancelTotal;
    }

    public LocalDate fechaUltimoPago(List<CuotaEntity> cuotas){
        LocalDate fechaActual = LocalDate.now();
        LocalDate fecha = fechaActual.plusYears(0).withMonth(4).withDayOfMonth(10);
        for(CuotaEntity cuota: cuotas){
            if(cuota.getPagada().equals("Pagada")){
                int resultado = fecha.compareTo(cuota.getFechaDePago());
                if (resultado < 0){
                    fecha = cuota.getFechaDePago();
                }
            }
        }
        return fecha;
    }

    public List<EstudianteEntity> findAllEstudiantes() {
        ResponseEntity<List<EstudianteEntity>> response = restTemplate.exchange(
                "http://backend-gateway-service:8080/estudiante/mostrarestudiante",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<EstudianteEntity>>() {}
        );
        return response.getBody();
    }

    public double montoTotalPagado(List<CuotaEntity> cuotas){
        double montoPagado=0;
        for (CuotaEntity cuota : cuotas){
            if (cuota.getPagada().equals("Pagada")){
                montoPagado = montoPagado + cuota.getMontoDescuentoI();
            }
        }
        return montoPagado;
    }
    public double saldoPorPagar(List<CuotaEntity> cuotas){
        double montoPorPagar=0;
        for (CuotaEntity cuota : cuotas){
            if (cuota.getPagada().equals("Pendiente")){
                montoPorPagar = montoPorPagar + cuota.getMontoDescuentoI();
            }
        }
        return montoPorPagar;
    }

    public int cuotaConRetraso(List<CuotaEntity> cuotas){
        int cuotasAtrasadas=0;
        LocalDate fechaActual = LocalDate.now();
        for (CuotaEntity cuota : cuotas){
            int resultado = fechaActual.compareTo(cuota.getFechaDePago());
            if (cuota.getPagada().equals("Pendiente") && resultado >0){
                cuotasAtrasadas = cuotasAtrasadas + 1;
            }
        }
        return cuotasAtrasadas;
    }


    public Long countByRutCuota(String rut){
        ResponseEntity<Long> response = restTemplate.exchange(
                "http://backend-gateway-service:8080/cuotas/contar/"+rut,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Long>() {}
        );
    return response.getBody();
    }

    public double calcularDescuento(int promedio){
        double descuento;
        if (promedio <= 1000 && promedio >=950){
            descuento = 0.9;
        } else if (promedio <= 949 && promedio >=900) {
            descuento = 0.95;
        } else if (promedio <= 899 && promedio >=850) {
            descuento = 0.98;
        }else{
            descuento = 1;
        }
        return descuento;
    }
    public List<CuotaEntity> findByRutCuota(String rut) {
        ResponseEntity<List<CuotaEntity>> response = restTemplate.exchange(
                "http://backend-gateway-service:8080/cuotas/"+rut,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CuotaEntity>>() {}
        );
        return response.getBody();
    }

    public void save(CuotaEntity cuota) {
        String url = "http://backend-gateway-service:8080/cuotas";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CuotaEntity> requestEntity = new HttpEntity<>(cuota, headers);

        ResponseEntity<CuotaEntity> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                CuotaEntity.class
        );
        responseEntity.getBody();
    }

    public boolean existenPruebasDeEstudiante(String rut) {
        double cantidadPruebas = pruebaRepository.countByRutPrueba(rut);
        return cantidadPruebas > 0;
    }

    public boolean mismoMesYAnio(LocalDate fecha1, LocalDate fecha2) {
        return fecha1.getMonth().equals(fecha2.getMonth()) && fecha1.getYear() == fecha2.getYear();
    }
}
