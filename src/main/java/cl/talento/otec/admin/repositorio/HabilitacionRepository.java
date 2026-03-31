package cl.talento.otec.admin.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.talento.otec.admin.modelo.Habilitacion;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface HabilitacionRepository extends JpaRepository<Habilitacion, Integer> {
    List<Habilitacion> findByRelator_IdRelator(Integer idRelator);
    
    boolean existsByRelator_IdRelatorAndCodigoEspecialidadSenceAndEstadoReufAndFechaVencimientoReufAfter(
            Integer idRelator, 
            String codigoEspecialidadSence, 
            String estadoReuf, 
            LocalDate fechaVencimiento);
}
