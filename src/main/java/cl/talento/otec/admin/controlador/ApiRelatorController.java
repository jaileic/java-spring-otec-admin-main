package cl.talento.otec.admin.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.talento.otec.admin.dto.RelatorDTO;
import cl.talento.otec.admin.servicio.RelatorService;

import java.util.List;

/**
 * Controlador REST para operaciones con Relatores
 * Proporciona endpoints para filtrar relatores según su habilitación SENCE
 */
@RestController
@RequestMapping("/api/relatores")
public class ApiRelatorController {

    private final RelatorService relatorService;

    public ApiRelatorController(RelatorService relatorService) {
        this.relatorService = relatorService;
    }

    /**
     * Busca relatores activos que posean una habilitación vigente para un código SENCE específico
     * 
     * @param codigoSence Código de especialidad SENCE a buscar
     * @return Lista de RelatorDTO con habilitaciones vigentes para el código especificado
     * 
     * Ejemplo: GET /api/relatores/habilitados?codigoSence=4015-001
     */
    @GetMapping("/habilitados")
    public ResponseEntity<List<RelatorDTO>> buscarRelatorHabilitados(
            @RequestParam String codigoSence) {
        List<RelatorDTO> relatores = relatorService.buscarHabilitadosPorCodigoSence(codigoSence);
        return ResponseEntity.ok(relatores);
    }
}
