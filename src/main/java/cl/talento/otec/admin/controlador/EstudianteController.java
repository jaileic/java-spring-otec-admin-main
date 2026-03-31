package cl.talento.otec.admin.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.talento.otec.admin.dto.EstudianteDTO;
import cl.talento.otec.admin.repositorio.CursoRepository;
import cl.talento.otec.admin.servicio.EstudianteService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;
    private final CursoRepository cursoRepository;

    public EstudianteController(EstudianteService estudianteService, CursoRepository cursoRepository) {
        this.estudianteService = estudianteService;
        this.cursoRepository = cursoRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("estudiantes", estudianteService.obtenerTodos());
        return "estudiantes/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("estudiante", new EstudianteDTO());
        model.addAttribute("cursos", cursoRepository.findByActivoTrue());
        return "estudiantes/formulario";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("estudiante") EstudianteDTO estudianteDTO,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("cursos", cursoRepository.findByActivoTrue());
            return "estudiantes/formulario";
        }

        estudianteService.guardar(estudianteDTO);
        return "redirect:/estudiantes";
    }
}
