package cl.talento.otec.admin.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.talento.otec.admin.modelo.Practica;
import cl.talento.otec.admin.repositorio.PracticaRepository;

@Service
public class PracticaService {

    private final PracticaRepository practicaRepository;

    public PracticaService(PracticaRepository practicaRepository) {
        this.practicaRepository = practicaRepository;
    }

    public List<Practica> listarTodas() {
        return practicaRepository.findAllByOrderByFechaEntregaAsc();
    }
}
