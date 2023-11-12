package backendplanilla.service.backendplanilla.service.Repository;

import backendplanilla.service.backendplanilla.service.Entities.ArancelEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArancelRepository extends CrudRepository<ArancelEntity, Integer> {

    boolean existsByRutEstudiante(String rutEstudiante);

    ArancelEntity findByRutEstudiante(String rutEstudiante);
}
