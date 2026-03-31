package cl.talento.otec.admin.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.talento.otec.admin.modelo.Estudiante;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

    List<Estudiante> findByActivoTrue();
}
