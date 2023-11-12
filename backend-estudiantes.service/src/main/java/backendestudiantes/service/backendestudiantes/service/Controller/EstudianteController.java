package backendestudiantes.service.backendestudiantes.service.Controller;

import backendestudiantes.service.backendestudiantes.service.Entities.EstudianteEntity;
import backendestudiantes.service.backendestudiantes.service.Service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/estudiante")
public class EstudianteController {
    @Autowired
    EstudianteService estudianteService;

    @PostMapping()
    public ResponseEntity<EstudianteEntity> newEstudiante(@RequestBody EstudianteEntity estudiante) {
        estudianteService.save(estudiante);
        return ResponseEntity.ok(estudiante);
    }

    @GetMapping("/mostrarestudiante")
    public ResponseEntity<List<EstudianteEntity>> listar() {
        List<EstudianteEntity> estudianteEntities = estudianteService.findAll();
        return ResponseEntity.ok(estudianteEntities);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<EstudianteEntity> findByRut(@PathVariable("rut") String rut) {
        EstudianteEntity estudianteEntity = estudianteService.findByRut(rut);
        System.out.println(estudianteEntity);
        return ResponseEntity.ok(estudianteEntity);
    }

}
