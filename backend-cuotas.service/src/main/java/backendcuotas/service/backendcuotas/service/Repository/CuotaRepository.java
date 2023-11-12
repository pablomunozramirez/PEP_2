package backendcuotas.service.backendcuotas.service.Repository;

import backendcuotas.service.backendcuotas.service.Entities.CuotaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;


@Repository
public interface CuotaRepository extends CrudRepository<CuotaEntity, Integer> {
    ArrayList<CuotaEntity> findByRutCuota(String rut);

    long countByRutCuota(String rutCuota);

    boolean existsByRutCuota(String rut);

    CuotaEntity findById (int id_cuota);

}
