package backendplanilla.service.backendplanilla.service.Controller;


import backendplanilla.service.backendplanilla.service.Entities.ArancelEntity;
import backendplanilla.service.backendplanilla.service.Service.ArancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@RestController
@RequestMapping("/arancel")
public class ArancelController {
    @Autowired
    ArancelService arancelService;

    @PostMapping("/generar")
    public ResponseEntity<Void> generarArancel() {
        arancelService.aplicarDescuentos();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/mostrar")
    public ResponseEntity<ArrayList<ArancelEntity>> mostrarArancel() {
        ArrayList<ArancelEntity> resumenList = arancelService.resumenArancel();
        return ResponseEntity.ok(resumenList);
    }

}
