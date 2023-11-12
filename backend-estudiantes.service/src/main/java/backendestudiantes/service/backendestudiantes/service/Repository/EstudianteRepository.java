package backendestudiantes.service.backendestudiantes.service.Repository;

import backendestudiantes.service.backendestudiantes.service.Entities.EstudianteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends CrudRepository<EstudianteEntity, String> {
    boolean existsByRut(String rut);

    @Query("select e from EstudianteEntity e where e.rut = :rut")
    EstudianteEntity findByRut(@Param("rut") String rut);
}
