package cl.talento.otec.admin.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;

import cl.talento.otec.admin.dto.RelatorDTO;
import cl.talento.otec.admin.servicio.RelatorService;

@Controller
@RequestMapping("/relatores")
public class RelatorController {

    private final RelatorService relatorService;

    public RelatorController(RelatorService relatorService) {
        this.relatorService = relatorService;
    }

    /**
     * Lista los actores activos
     */
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("relatores", relatorService.listarActivos());
        return "relatores/lista";
    }

    /**
     * Muestra formulario para crear nuevo relator
     */
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("relator", new RelatorDTO());
        return "relatores/formulario";
    }

    /**
     * Guarda un nuevo relator o actualiza uno existente
     */
    @PostMapping("/guardar")
    public String guardar(
            @Valid @ModelAttribute("relator") RelatorDTO relatorDTO,
            BindingResult result,
            Model model) {
        
        if (result.hasErrors()) {
            return "relatores/formulario";
        }

        relatorService.guardar(relatorDTO);
        return "redirect:/relatores";
    }

    /**
     * Muestra formulario para editar un relator existente
     */
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        RelatorDTO relator = relatorService.obtenerPorId(id);
        model.addAttribute("relator", relator);
        return "relatores/formulario";
    }

    /**
     * Elimina un relator (Borrado Lógico)
     */
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        relatorService.eliminar(id);
        return "redirect:/relatores";
    }
}
