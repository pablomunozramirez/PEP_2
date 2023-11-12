package backendcuotas.service.backendcuotas.service.Service;

import backendcuotas.service.backendcuotas.service.Entities.CuotaEntity;
import backendcuotas.service.backendcuotas.service.Model.EstudianteEntity;
import backendcuotas.service.backendcuotas.service.Repository.CuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class CuotaService {
    @Autowired
    CuotaService cuotaService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CuotaRepository cuotaRepository;

    public void generarCuota (EstudianteEntity estudiante, int numero_Cuotas){
        if (numero_Cuotas == 1){
            int valorCuotas = 1500000/2;
            CuotaEntity cuota = new CuotaEntity();
            cuota.setNumero_cuota(1);
            cuota.setMonto(valorCuotas);
            cuota.setMontoDescuentoP(valorCuotas);
            cuota.setMontoDescuentoI(valorCuotas);
            cuota.setPagada("Pagada");
            cuota.setRutCuota(estudiante.getRut());
            cuota.setFechaCuota(LocalDate.parse("2023-04-01"));
            cuota.setFechaDePago(LocalDate.parse("2023-04-01"));
            cuotaRepository.save(cuota);
        }else{
            double arancelDescuento = calcularArancel(estudiante);
            int valorCuotas = (int)arancelDescuento/numero_Cuotas;
            LocalDate fecha = LocalDate.parse("2023-04-10");
            for (int i = 1; i<= numero_Cuotas; i++){
                CuotaEntity cuota = new CuotaEntity();
                cuota.setNumero_cuota(i);
                cuota.setRutCuota(estudiante.getRut());
                cuota.setMonto(valorCuotas);
                cuota.setMontoDescuentoP(valorCuotas);
                cuota.setMontoDescuentoI(valorCuotas);
                cuota.setPagada("Pendiente");
                cuota.setFechaCuota(LocalDate.parse("2023-04-01"));
                LocalDate fechaCuota = fecha.plusMonths(i-1);
                cuota.setFechaDePago(fechaCuota);
                cuotaRepository.save(cuota);
            }
        }
    }

    public double calcularArancel(EstudianteEntity estudiante) {
        double arancel = 1500000;
        if (estudiante.getTipo_colegio().equals("1")){
            arancel = arancel * 0.8;
        } else if (estudiante.getTipo_colegio().equals("2")){
            arancel = arancel * 0.9;
        }
        LocalDate current_date = LocalDate.now();
        int current_Year = current_date.getYear();
        int egreso = estudiante.getAnio_egreso();
        if(current_Year - egreso == 0){
            arancel = arancel * 0.85;
        } else if (current_Year - egreso < 3) {
            arancel = arancel * 0.92;
        } else if (current_Year - egreso < 5) {
            arancel = arancel*0.96;
        }
        return arancel;
    }
    public ArrayList<CuotaEntity> obternerCuotaPorRut(String rut){
        return cuotaRepository.findByRutCuota(rut);
    }

    public EstudianteEntity findByRut(String rut){
        System.out.println("rut: "+rut);
        ResponseEntity<EstudianteEntity> response = restTemplate.exchange(
                "http://localhost:8001/estudiante/"+rut,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<EstudianteEntity>() {}
        );
        return response.getBody();
    }



    public CuotaEntity cambiarEstadoDePagoCuota(CuotaEntity cuota1){
        cuota1.setPagada("Pagada");
        cuotaRepository.save(cuota1);
        return cuota1;
    }

    public CuotaEntity findById(int id_cuota){return cuotaRepository.findById(id_cuota);}

    public Long countByRutCuota(String rut){return cuotaRepository.countByRutCuota(rut);}

    public void save(CuotaEntity cuota){cuotaRepository.save(cuota);}

}
