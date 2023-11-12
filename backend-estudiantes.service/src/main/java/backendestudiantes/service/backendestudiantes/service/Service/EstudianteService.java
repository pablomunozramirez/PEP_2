package backendestudiantes.service.backendestudiantes.service.Service;

import backendestudiantes.service.backendestudiantes.service.Entities.EstudianteEntity;
import backendestudiantes.service.backendestudiantes.service.Repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class EstudianteService {

    @Autowired
    EstudianteRepository estudianteRepository;

    public void save(EstudianteEntity estudianteEntity){
        estudianteRepository.save(estudianteEntity);
    }

    public List<EstudianteEntity> findAll(){
        return (List<EstudianteEntity>) estudianteRepository.findAll();
    }

    public EstudianteEntity findByRut(String rut){
        return estudianteRepository.findByRut(rut);
    }
}
