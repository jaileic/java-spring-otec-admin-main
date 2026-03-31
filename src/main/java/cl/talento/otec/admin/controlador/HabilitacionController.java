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

import cl.talento.otec.admin.dto.HabilitacionDTO;
import cl.talento.otec.admin.dto.RelatorDTO;
import cl.talento.otec.admin.servicio.HabilitacionService;
import cl.talento.otec.admin.servicio.RelatorService;

@Controller
@RequestMapping("/relatores/{idRelator}/habilitaciones")
public class HabilitacionController {

    private final HabilitacionService habilitacionService;
    private final RelatorService relatorService;

    public HabilitacionController(HabilitacionService habilitacionService, RelatorService relatorService) {
        this.habilitacionService = habilitacionService;
        this.relatorService = relatorService;
    }

    /**
     * Muestra la lista de habilitaciones de un relator con formulario para agregar nuevas
     */
    @GetMapping
    public String listar(@PathVariable Integer idRelator, Model model) {
        RelatorDTO relator = relatorService.obtenerPorId(idRelator);
        model.addAttribute("relator", relator);
        model.addAttribute("habilitaciones", habilitacionService.listarPorRelator(idRelator));
        model.addAttribute("habilitacion", new HabilitacionDTO());
        return "relatores/habilitaciones";
    }

    /**
     * Guarda una nueva habilitación o actualiza una existente
     */
    @PostMapping("/guardar")
    public String guardar(@PathVariable Integer idRelator,
                         @Valid @ModelAttribute("habilitacion") HabilitacionDTO dto,
                         BindingResult result,
                         Model model) {
        
        if (result.hasErrors()) {
            // Recargar los datos si hay errores
            RelatorDTO relator = relatorService.obtenerPorId(idRelator);
            model.addAttribute("relator", relator);
            model.addAttribute("habilitaciones", habilitacionService.listarPorRelator(idRelator));
            return "relatores/habilitaciones";
        }

        habilitacionService.guardar(dto, idRelator);
        return "redirect:/relatores/{idRelator}/habilitaciones";
    }

    /**
     * Elimina una habilitación
     */
    @GetMapping("/eliminar/{idHabilitacion}")
    public String eliminar(@PathVariable Integer idRelator, @PathVariable Integer idHabilitacion) {
        habilitacionService.eliminar(idHabilitacion);
        return "redirect:/relatores/{idRelator}/habilitaciones";
    }
}
