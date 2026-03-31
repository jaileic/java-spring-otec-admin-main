package cl.talento.otec.admin.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.talento.otec.admin.modelo.Evaluacion;
import cl.talento.otec.admin.repositorio.EvaluacionRepository;

@Service
public class EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;

    public EvaluacionService(EvaluacionRepository evaluacionRepository) {
        this.evaluacionRepository = evaluacionRepository;
    }

    public List<Evaluacion> listarTodas() {
        return evaluacionRepository.findAllByOrderByFechaEvaluacionDesc();
    }
}
