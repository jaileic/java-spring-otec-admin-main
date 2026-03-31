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

import cl.talento.otec.admin.dto.CursoDTO;
import cl.talento.otec.admin.servicio.CursoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cursos")
public class ApiCursoController {

    private final CursoService cursoService;

    public ApiCursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public List<CursoDTO> listar() {
        return cursoService.obtenerTodosCursos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> obtener(@PathVariable Integer id) {
        CursoDTO curso = cursoService.obtenerCursoPorId(id);
        return curso != null ? ResponseEntity.ok(curso) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CursoDTO> crear(@Valid @RequestBody CursoDTO cursoDTO) {
        CursoDTO creado = cursoService.guardarCurso(cursoDTO);
        return ResponseEntity.created(URI.create("/api/cursos/" + creado.getIdCurso())).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody CursoDTO cursoDTO) {
        if (cursoService.obtenerCursoPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }

        cursoDTO.setIdCurso(id);
        return ResponseEntity.ok(cursoService.guardarCurso(cursoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (cursoService.obtenerCursoPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }

        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }
}
