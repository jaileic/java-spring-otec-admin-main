package cl.talento.otec.admin.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;

import cl.talento.otec.admin.dto.CursoDTO;
import cl.talento.otec.admin.servicio.CursoService;
import cl.talento.otec.admin.servicio.RelatorService;
import cl.talento.otec.admin.repositorio.RelatorRepository;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;
    private final RelatorService relatorService;
    private final RelatorRepository relatorRepository;

    public CursoController(CursoService cursoService, RelatorService relatorService,
            RelatorRepository relatorRepository) {
        this.cursoService = cursoService;
        this.relatorService = relatorService;
        this.relatorRepository = relatorRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("cursos", cursoService.obtenerTodosCursos());
        return "cursos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("curso", new CursoDTO());
        model.addAttribute("relatores", relatorRepository.findByActivoTrue());
        return "nuevo-curso";
    }

    @PostMapping
    public String guardarCurso(@Valid @ModelAttribute("curso") CursoDTO cursoDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("relatores", relatorRepository.findByActivoTrue());
            result.getAllErrors().forEach(error -> System.err.println("Error: " + error.getDefaultMessage()));
            return "nuevo-curso";
        }
        try {
            cursoService.guardarCurso(cursoDTO);
            return "redirect:/cursos";
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar: " + e.getMessage());
            model.addAttribute("relatores", relatorRepository.findByActivoTrue());
            e.printStackTrace();
            return "nuevo-curso";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model) {
        CursoDTO curso = cursoService.obtenerCursoPorId(id);
        if (curso == null) {
            return "redirect:/cursos";
        }
        model.addAttribute("curso", curso);
        // Filtrar relatores habilitados para el código SENCE del curso
        model.addAttribute("relatores", relatorService.buscarHabilitadosPorCodigoSence(curso.getCodigoSence()));
        return "nuevo-curso";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCurso(@PathVariable("id") Integer id) {
        cursoService.eliminarCurso(id);
        return "redirect:/cursos";
    }

    @GetMapping("/inactivos")
    public String listarInactivos(Model model) {
        model.addAttribute("cursos", cursoService.listarInactivos());
        return "archivo-cursos";
    }

    @GetMapping("/restaurar/{id}")
    public String restaurarCurso(@PathVariable("id") Integer id) {
        cursoService.restaurar(id);
        return "redirect:/cursos/inactivos";
    }
}
