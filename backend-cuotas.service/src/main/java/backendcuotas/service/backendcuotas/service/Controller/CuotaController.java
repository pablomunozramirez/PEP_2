package backendcuotas.service.backendcuotas.service.Controller;

import backendcuotas.service.backendcuotas.service.Entities.CuotaEntity;
import backendcuotas.service.backendcuotas.service.Model.EstudianteEntity;
import backendcuotas.service.backendcuotas.service.Service.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuotas")
public class CuotaController {
    @Autowired
    CuotaService cuotaService;

    @GetMapping("/{rut}/{cuotas}")
    public ResponseEntity<List<CuotaEntity>> cuotas(@PathVariable("rut") String rut, @PathVariable("cuotas") String cuotas){
        EstudianteEntity estudianteEntity = cuotaService.findByRut(rut);
        System.out.println(estudianteEntity);
        if(estudianteEntity != null){
            if(cuotaService.obternerCuotaPorRut(estudianteEntity.getRut()).isEmpty()){
                cuotaService.generarCuota(estudianteEntity, Integer.parseInt(cuotas));
                List<CuotaEntity> cuotasEntities = cuotaService.obternerCuotaPorRut(estudianteEntity.getRut());
                System.out.println(cuotasEntities);
                return ResponseEntity.ok(cuotasEntities);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{rut}")
    public ResponseEntity<List<CuotaEntity>> listado(@PathVariable("rut") String rut){
        System.out.println("Listar");
        System.out.println("rut: "+rut+"\n");
        EstudianteEntity estudianteEntity = cuotaService.findByRut(rut);
        System.out.println(estudianteEntity);
        if(estudianteEntity != null){
            List<CuotaEntity> cuotasEntities = cuotaService.obternerCuotaPorRut(estudianteEntity.getRut());
            return ResponseEntity.ok(cuotasEntities);
        }
        return ResponseEntity.ok(null);
    }
    @PutMapping("/pagar/{id_cuota}")
    public ResponseEntity<CuotaEntity> pagar(@PathVariable("id_cuota") int id_cuota){
        CuotaEntity cuota = cuotaService.findById(id_cuota);
        CuotaEntity cuota1 = cuotaService.cambiarEstadoDePagoCuota(cuota);
        return ResponseEntity.ok(cuota1);
    }

    @GetMapping("/contar/{rut}")
    public ResponseEntity<Long> contarCuotas(@PathVariable("rut") String rut){
        Long cantidadCuotas = cuotaService.countByRutCuota(rut);
        return ResponseEntity.ok(cantidadCuotas);
    }

    @PutMapping()
    public ResponseEntity<CuotaEntity> actualizarCuota(@RequestBody CuotaEntity cuota) {
        cuotaService.save(cuota);
        return ResponseEntity.ok(cuota);
    }

}
