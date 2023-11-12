package backendplanilla.service.backendplanilla.service.Repository;

import backendplanilla.service.backendplanilla.service.Entities.PruebaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PruebaRepository extends CrudRepository<PruebaEntity, Integer> {

    ArrayList<PruebaEntity> findByRutPrueba(String rut);

    long countByRutPrueba(String rut);
}
