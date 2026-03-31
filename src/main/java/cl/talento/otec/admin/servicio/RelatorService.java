package cl.talento.otec.admin.servicio;

import org.springframework.stereotype.Service;
import cl.talento.otec.admin.dto.RelatorDTO;
import cl.talento.otec.admin.modelo.Relator;
import cl.talento.otec.admin.repositorio.RelatorRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RelatorService {

    private final RelatorRepository relatorRepository;

    public RelatorService(RelatorRepository relatorRepository) {
        this.relatorRepository = relatorRepository;
    }

    /**
     * Lista todos los relatores activos y los mapea a DTOs
     */
    public List<RelatorDTO> listarActivos() {
        return relatorRepository.findByActivoTrue()
            .stream()
            .map(this::mapRelatorToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Obtiene un relator por ID y lo mapea a DTO
     */
    public RelatorDTO obtenerPorId(Integer id) {
        Optional<Relator> relator = relatorRepository.findById(id);
        if (relator.isPresent()) {
            return mapRelatorToDTO(relator.get());
        }
        throw new IllegalArgumentException("Relator no encontrado con ID: " + id);
    }

    /**
     * Busca relatores activos que posean una habilitación vigente para un código SENCE específico
     * Retorna lista de DTOs mapeados
     */
    public List<RelatorDTO> buscarHabilitadosPorCodigoSence(String codigoSence) {
        return relatorRepository.findByActivoTrueAndHabilitacionesVigentes(
            codigoSence,
            "Vigente",
            LocalDate.now()
        )
        .stream()
        .map(this::mapRelatorToDTO)
        .collect(Collectors.toList());
    }

    /**
     * Guarda un nuevo relator o actualiza uno existente
     * Si el DTO trae un ID, actualiza; si no, crea uno nuevo con activo = true
     */
    public void guardar(RelatorDTO dto) {
        Relator relator;

        if (dto.getIdRelator() != null) {
            // Actualizar existente
            Optional<Relator> existente = relatorRepository.findById(dto.getIdRelator());
            if (existente.isPresent()) {
                relator = existente.get();
            } else {
                throw new IllegalArgumentException("Relator no encontrado con ID: " + dto.getIdRelator());
            }
        } else {
            // Crear nuevo
            relator = new Relator();
            relator.setActivo(true);
        }

        // Mapear DTO a entidad
        relator.setRut(dto.getRut());
        relator.setNombres(dto.getNombres());
        relator.setApellidos(dto.getApellidos());
        relator.setEmail(dto.getEmail());
        relator.setProfesionTitulo(dto.getProfesionTitulo());

        relatorRepository.save(relator);
    }

    /**
     * Implementa Borrado Lógico: busca la entidad, aplica setActivo(false) y guarda
     */
    public void eliminar(Integer id) {
        Optional<Relator> relator = relatorRepository.findById(id);
        if (relator.isPresent()) {
            relator.get().setActivo(false);
            relatorRepository.save(relator.get());
        } else {
            throw new IllegalArgumentException("Relator no encontrado con ID: " + id);
        }
    }

    /**
     * Mapea Relator a RelatorDTO
     */
    private RelatorDTO mapRelatorToDTO(Relator relator) {
        RelatorDTO dto = new RelatorDTO();
        dto.setIdRelator(relator.getIdRelator());
        dto.setRut(relator.getRut());
        dto.setNombres(relator.getNombres());
        dto.setApellidos(relator.getApellidos());
        dto.setEmail(relator.getEmail());
        dto.setProfesionTitulo(relator.getProfesionTitulo());
        dto.setActivo(relator.getActivo());
        return dto;
    }
}
