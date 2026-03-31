package cl.talento.otec.admin.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.talento.otec.admin.dto.EstudianteDTO;
import cl.talento.otec.admin.modelo.Curso;
import cl.talento.otec.admin.modelo.Estudiante;
import cl.talento.otec.admin.repositorio.CursoRepository;
import cl.talento.otec.admin.repositorio.EstudianteRepository;

@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;

    public EstudianteService(EstudianteRepository estudianteRepository, CursoRepository cursoRepository) {
        this.estudianteRepository = estudianteRepository;
        this.cursoRepository = cursoRepository;
    }

    public List<EstudianteDTO> obtenerTodos() {
        return estudianteRepository.findByActivoTrue().stream()
                .map(this::mapToDTO)
                .toList();
    }

    public EstudianteDTO obtenerPorId(Integer id) {
        return estudianteRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    public EstudianteDTO guardar(EstudianteDTO estudianteDTO) {
        Estudiante estudiante = estudianteDTO.getIdEstudiante() != null
                ? estudianteRepository.findById(estudianteDTO.getIdEstudiante()).orElse(new Estudiante())
                : new Estudiante();

        estudiante.setRut(estudianteDTO.getRut());
        estudiante.setNombres(estudianteDTO.getNombres());
        estudiante.setApellidos(estudianteDTO.getApellidos());
        estudiante.setEmail(estudianteDTO.getEmail());
        estudiante.setPrograma(estudianteDTO.getPrograma());
        estudiante.setActivo(estudianteDTO.getActivo() != null ? estudianteDTO.getActivo() : true);

        if (estudianteDTO.getIdCurso() != null) {
            Optional<Curso> curso = cursoRepository.findById(estudianteDTO.getIdCurso());
            estudiante.setCurso(curso.orElse(null));
        } else {
            estudiante.setCurso(null);
        }

        return mapToDTO(estudianteRepository.save(estudiante));
    }

    public void eliminar(Integer id) {
        estudianteRepository.findById(id).ifPresent(estudiante -> {
            estudiante.setActivo(false);
            estudianteRepository.save(estudiante);
        });
    }

    private EstudianteDTO mapToDTO(Estudiante estudiante) {
        EstudianteDTO dto = new EstudianteDTO();
        dto.setIdEstudiante(estudiante.getIdEstudiante());
        dto.setRut(estudiante.getRut());
        dto.setNombres(estudiante.getNombres());
        dto.setApellidos(estudiante.getApellidos());
        dto.setEmail(estudiante.getEmail());
        dto.setPrograma(estudiante.getPrograma());
        dto.setActivo(estudiante.getActivo());

        if (estudiante.getCurso() != null) {
            dto.setIdCurso(estudiante.getCurso().getIdCurso());
            dto.setNombreCurso(estudiante.getCurso().getNombre());
        } else {
            dto.setNombreCurso("Sin curso asignado");
        }

        return dto;
    }
}
