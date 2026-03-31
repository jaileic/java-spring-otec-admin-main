package cl.talento.otec.admin.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.talento.otec.admin.modelo.Curso;
import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {
    List<Curso> findByActivoTrue();

    List<Curso> findByActivoFalse();

    List<Curso> findByArchivadoFalse();

    List<Curso> findByArchivadoTrue();
}
