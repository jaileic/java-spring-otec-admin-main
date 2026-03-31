package cl.talento.otec.admin.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.talento.otec.admin.modelo.Practica;

@Repository
public interface PracticaRepository extends JpaRepository<Practica, Integer> {

    List<Practica> findAllByOrderByFechaEntregaAsc();
}
