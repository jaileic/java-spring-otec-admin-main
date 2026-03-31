package cl.talento.otec.admin.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.talento.otec.admin.modelo.Relator;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RelatorRepository extends JpaRepository<Relator, Integer> {
    List<Relator> findByActivoTrue();

    /**
     * Busca relatores activos que posean una habilitación vigente para un código SENCE específico
     * Filtra por: activo=true, estado REUF="Vigente", fecha vencimiento > fecha actual
     */
    @Query("""
        SELECT DISTINCT r FROM Relator r 
        INNER JOIN r.habilitaciones h 
        WHERE r.activo = true 
        AND h.codigoEspecialidadSence = :codigoSence 
        AND h.estadoReuf = :estadoReuf 
        AND h.fechaVencimientoReuf > :fechaActual
        """)
    List<Relator> findByActivoTrueAndHabilitacionesVigentes(
        @Param("codigoSence") String codigoSence,
        @Param("estadoReuf") String estadoReuf,
        @Param("fechaActual") LocalDate fechaActual
    );
}
