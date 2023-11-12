package backendplanilla.service.backendplanilla.service.Service;

import backendplanilla.service.backendplanilla.service.Entities.PruebaEntity;
import backendplanilla.service.backendplanilla.service.Repository.PruebaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
public class PruebaService {

    @Autowired
    PruebaRepository pruebaRepository;

    private final Logger logg = LoggerFactory.getLogger(PruebaService.class);

    public ArrayList<PruebaEntity> obtenerData() {
        return (ArrayList<PruebaEntity>) pruebaRepository.findAll();
    }

    public String guardar(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    PruebaEntity prueba = new PruebaEntity();
                    prueba.setRutPrueba(parts[0]);
                    prueba.setFecha_examen(LocalDate.parse(parts[1], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    prueba.setPuntaje(Integer.parseInt(parts[2]));
                    System.out.println(prueba);
                    pruebaRepository.save(prueba);
                } else {
                    logg.warn("Formato incorrecto en línea CSV: {}", line);
                }
            }

            return "Datos cargados con éxito";
        } catch (IOException e) {
            logg.error("Error al leer el archivo CSV", e);
            return "Error al leer el archivo CSV";
        }
    }

    public int calcularPromedioPruebas(String rut){
        ArrayList<PruebaEntity> pruebas = pruebaRepository.findByRutPrueba(rut);
        int puntaje = 0;
        int numeroExamenes = pruebas.size();
        for (PruebaEntity prueba : pruebas) {
            int aux = prueba.getPuntaje();
            puntaje = puntaje + aux;
        }
        if (puntaje != 0){
            return puntaje /numeroExamenes;
        }
        return 0;
    }

    public ArrayList<PruebaEntity> obtenerpruebas (){
        return (ArrayList<PruebaEntity>) pruebaRepository.findAll();
    }
}
