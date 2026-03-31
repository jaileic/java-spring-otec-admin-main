package cl.talento.otec.admin.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.talento.otec.admin.servicio.EvaluacionService;

@Controller
@RequestMapping("/evaluaciones")
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    public EvaluacionController(EvaluacionService evaluacionService) {
        this.evaluacionService = evaluacionService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("evaluaciones", evaluacionService.listarTodas());
        return "evaluaciones/lista";
    }
}
