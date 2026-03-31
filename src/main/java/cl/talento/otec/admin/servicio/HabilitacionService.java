package cl.talento.otec.admin.servicio;

import org.springframework.stereotype.Service;
import cl.talento.otec.admin.dto.HabilitacionDTO;
import cl.talento.otec.admin.modelo.Habilitacion;
import cl.talento.otec.admin.modelo.Relator;
import cl.talento.otec.admin.repositorio.HabilitacionRepository;
import cl.talento.otec.admin.repositorio.RelatorRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HabilitacionService {

    private final HabilitacionRepository habilitacionRepository;
    private final RelatorRepository relatorRepository;

    public HabilitacionService(HabilitacionRepository habilitacionRepository, RelatorRepository relatorRepository) {
        this.habilitacionRepository = habilitacionRepository;
        this.relatorRepository = relatorRepository;
    }

    /**
     * Lista todas las habilitaciones de un relator específico
     */
    public List<HabilitacionDTO> listarPorRelator(Integer idRelator) {
        return habilitacionRepository.findByRelator_IdRelator(idRelator)
            .stream()
            .map(this::mapHabilitacionToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Guarda una nueva habilitación o actualiza una existente
     */
    public void guardar(HabilitacionDTO dto, Integer idRelator) {
        Optional<Relator> relator = relatorRepository.findById(idRelator);
        if (!relator.isPresent()) {
            throw new IllegalArgumentException("Relator no encontrado con ID: " + idRelator);
        }

        Habilitacion habilitacion;
        if (dto.getIdHabilitacion() != null) {
            // Actualizar existente
            Optional<Habilitacion> existente = habilitacionRepository.findById(dto.getIdHabilitacion());
            if (existente.isPresent()) {
                habilitacion = existente.get();
            } else {
                throw new IllegalArgumentException("Habilitación no encontrada con ID: " + dto.getIdHabilitacion());
            }
        } else {
            // Crear nueva
            habilitacion = new Habilitacion();
        }

        // Mapear DTO a entidad
        habilitacion.setAreaCurso(dto.getAreaCurso());
        habilitacion.setCodigoEspecialidadSence(dto.getCodigoEspecialidadSence());
        habilitacion.setEstadoReuf(dto.getEstadoReuf());
        habilitacion.setFechaVencimientoReuf(dto.getFechaVencimientoReuf());
        habilitacion.setEstado(dto.getEstado());
        habilitacion.setRelator(relator.get());

        habilitacionRepository.save(habilitacion);
    }

    /**
     * Elimina una habilitación (eliminación física, no lógica)
     */
    public void eliminar(Integer idHabilitacion) {
        if (!habilitacionRepository.existsById(idHabilitacion)) {
            throw new IllegalArgumentException("Habilitación no encontrada con ID: " + idHabilitacion);
        }
        habilitacionRepository.deleteById(idHabilitacion);
    }

    /**
     * Valida si un relator está habilitado por SENCE para un código específico
     * Verifica que tenga una habilitación vigente con fecha de vencimiento posterior a hoy
     */
    public boolean relatorEstaHabilitado(Integer idRelator, String codigoSence) {
        return habilitacionRepository.existsByRelator_IdRelatorAndCodigoEspecialidadSenceAndEstadoReufAndFechaVencimientoReufAfter(
                idRelator, 
                codigoSence, 
                "Vigente", 
                LocalDate.now());
    }

    /**
     * Mapea Habilitacion a HabilitacionDTO
     */
    private HabilitacionDTO mapHabilitacionToDTO(Habilitacion habilitacion) {
        HabilitacionDTO dto = new HabilitacionDTO();
        dto.setIdHabilitacion(habilitacion.getIdHabilitacion());
        dto.setIdRelator(habilitacion.getRelator() != null ? habilitacion.getRelator().getIdRelator() : null);
        dto.setAreaCurso(habilitacion.getAreaCurso());
        dto.setCodigoEspecialidadSence(habilitacion.getCodigoEspecialidadSence());
        dto.setEstadoReuf(habilitacion.getEstadoReuf());
        dto.setFechaVencimientoReuf(habilitacion.getFechaVencimientoReuf());
        dto.setEstado(habilitacion.getEstado());
        return dto;
    }
}
