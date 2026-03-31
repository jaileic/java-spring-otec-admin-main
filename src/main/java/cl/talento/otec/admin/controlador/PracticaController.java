package cl.talento.otec.admin.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.talento.otec.admin.servicio.PracticaService;

@Controller
@RequestMapping("/practicas")
public class PracticaController {

    private final PracticaService practicaService;

    public PracticaController(PracticaService practicaService) {
        this.practicaService = practicaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("practicas", practicaService.listarTodas());
        return "practicas/lista";
    }
}
