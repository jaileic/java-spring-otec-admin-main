package cl.talento.otec.admin.controlador;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.talento.otec.admin.dto.EstudianteDTO;
import cl.talento.otec.admin.servicio.EstudianteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/estudiantes")
public class ApiEstudianteController {

    private final EstudianteService estudianteService;

    public ApiEstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public List<EstudianteDTO> listar() {
        return estudianteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDTO> obtener(@PathVariable Integer id) {
        EstudianteDTO estudiante = estudianteService.obtenerPorId(id);
        return estudiante != null ? ResponseEntity.ok(estudiante) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EstudianteDTO> crear(@Valid @RequestBody EstudianteDTO estudianteDTO) {
        EstudianteDTO creado = estudianteService.guardar(estudianteDTO);
        return ResponseEntity.created(URI.create("/api/estudiantes/" + creado.getIdEstudiante())).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudianteDTO> actualizar(@PathVariable Integer id,
            @Valid @RequestBody EstudianteDTO estudianteDTO) {
        if (estudianteService.obtenerPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }

        estudianteDTO.setIdEstudiante(id);
        return ResponseEntity.ok(estudianteService.guardar(estudianteDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (estudianteService.obtenerPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }

        estudianteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
