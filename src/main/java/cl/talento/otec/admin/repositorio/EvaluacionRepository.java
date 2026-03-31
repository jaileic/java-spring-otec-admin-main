package cl.talento.otec.admin.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.talento.otec.admin.modelo.Evaluacion;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Integer> {

    List<Evaluacion> findAllByOrderByFechaEvaluacionDesc();
}
