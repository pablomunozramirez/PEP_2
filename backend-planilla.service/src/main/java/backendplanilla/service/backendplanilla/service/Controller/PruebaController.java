package backendplanilla.service.backendplanilla.service.Controller;

import backendplanilla.service.backendplanilla.service.Entities.PruebaEntity;
import backendplanilla.service.backendplanilla.service.Service.PruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/arancel/prueba")
public class PruebaController {
    @Autowired
    PruebaService pruebaService;

    @PostMapping("/cargar")
    public ResponseEntity<String> cargarDatos(@RequestParam("file") MultipartFile file, Model model) {
        String mensaje = pruebaService.guardar(file);
        model.addAttribute("mensaje", mensaje);
        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/mostrarpruebas")
    public ResponseEntity<List<PruebaEntity>> mostrarPruebas(Model model){
        ArrayList<PruebaEntity> pruebas = pruebaService.obtenerpruebas();
        model.addAttribute("pruebas",pruebas);
        return ResponseEntity.ok(pruebas);
    }
}
