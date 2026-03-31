package cl.talento.otec.admin.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.talento.otec.admin.dto.CursoDTO;
import cl.talento.otec.admin.modelo.Curso;
import cl.talento.otec.admin.modelo.Relator;
import cl.talento.otec.admin.repositorio.CursoRepository;
import cl.talento.otec.admin.repositorio.RelatorRepository;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final RelatorRepository relatorRepository;

    public CursoService(CursoRepository cursoRepository, RelatorRepository relatorRepository) {
        this.cursoRepository = cursoRepository;
        this.relatorRepository = relatorRepository;
    }

    public List<CursoDTO> obtenerTodosCursos() {
        return cursoRepository.findByActivoTrue().stream()
                .map(this::mapCursoToDTO)
                .toList();
    }

    private CursoDTO mapCursoToDTO(Curso curso) {
        if (curso == null) {
            return null;
        }

        String nombreRelator = "Sin Relator Asignado";
        Integer idRelator = null;

        try {
            Relator relator = curso.getRelator();
            if (relator != null) {
                idRelator = relator.getIdRelator();
                String nombres = relator.getNombres() != null ? relator.getNombres().trim() : "";
                String apellidos = relator.getApellidos() != null ? relator.getApellidos().trim() : "";
                String nombreCompleto = (nombres + " " + apellidos).trim();
                if (!nombreCompleto.isEmpty()) {
                    nombreRelator = nombreCompleto;
                }
            }
        } catch (Exception e) {
            System.err.println("Error al mapear relator: " + e.getMessage());
            nombreRelator = "Sin Relator Asignado";
        }

        return new CursoDTO(
                curso.getIdCurso(),
                curso.getCodigoInterno(),
                curso.getCodigo(),
                curso.getNombre(),
                idRelator,
                nombreRelator,
                curso.getDuracionHoras(),
                curso.getCodigoSence(),
                curso.getCategoria(),
                curso.getActivo(),
                curso.getArchivado());
    }

    public CursoDTO guardarCurso(CursoDTO cursoDTO) {
        if (cursoDTO == null) {
            throw new IllegalArgumentException("CursoDTO no puede ser nulo");
        }

        Curso curso;

        if (cursoDTO.getIdCurso() != null) {
            Optional<Curso> existente = cursoRepository.findById(cursoDTO.getIdCurso());
            if (!existente.isPresent()) {
                throw new IllegalArgumentException("Curso no encontrado");
            }
            curso = existente.get();
        } else {
            curso = new Curso();
        }

        curso.setCodigoInterno(cursoDTO.getCodigoInterno() != null ? cursoDTO.getCodigoInterno() : "");
        curso.setCodigo(cursoDTO.getCodigo());
        curso.setNombre(cursoDTO.getNombre());
        curso.setDuracionHoras(cursoDTO.getDuracionHoras());
        curso.setCodigoSence(cursoDTO.getCodigoSence());
        curso.setCategoria(cursoDTO.getCategoria() != null ? cursoDTO.getCategoria() : "");
        curso.setActivo(cursoDTO.getActivo() != null ? cursoDTO.getActivo() : true);
        curso.setArchivado(cursoDTO.getArchivado() != null ? cursoDTO.getArchivado() : false);

        if (cursoDTO.getIdRelator() != null) {
            Optional<Relator> relator = relatorRepository.findById(cursoDTO.getIdRelator());
            if (relator.isPresent()) {
                curso.setRelator(relator.get());
            } else {
                throw new IllegalArgumentException("Relator no encontrado");
            }
        } else {
            curso.setRelator(null);
        }

        Curso cursoGuardado = cursoRepository.save(curso);
        return mapCursoToDTO(cursoGuardado);
    }

    public CursoDTO obtenerCursoPorId(Integer id) {
        if (id == null) {
            return null;
        }
        Optional<Curso> curso = cursoRepository.findById(id);
        return curso.map(this::mapCursoToDTO).orElse(null);
    }

    public void eliminarCurso(Integer id) {
        if (id != null) {
            Optional<Curso> curso = cursoRepository.findById(id);
            if (curso.isPresent()) {
                Curso cursoAEliminar = curso.get();
                cursoAEliminar.setActivo(false);
                cursoAEliminar.setArchivado(true);
                cursoRepository.save(cursoAEliminar);
            }
        }
    }

    public List<CursoDTO> listarInactivos() {
        return cursoRepository.findByActivoFalse().stream()
                .map(this::mapCursoToDTO)
                .toList();
    }

    public void restaurar(Integer id) {
        if (id != null) {
            Optional<Curso> curso = cursoRepository.findById(id);
            if (curso.isPresent()) {
                Curso cursoARestaurar = curso.get();
                cursoARestaurar.setArchivado(false);
                cursoARestaurar.setActivo(true);
                cursoRepository.save(cursoARestaurar);
            }
        }
    }
}
