package cl.talento.otec.admin.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import cl.talento.otec.admin.modelo.Curso;
import cl.talento.otec.admin.modelo.Estudiante;
import cl.talento.otec.admin.modelo.Evaluacion;
import cl.talento.otec.admin.modelo.Practica;
import cl.talento.otec.admin.modelo.Usuario;
import cl.talento.otec.admin.repositorio.CursoRepository;
import cl.talento.otec.admin.repositorio.EstudianteRepository;
import cl.talento.otec.admin.repositorio.EvaluacionRepository;
import cl.talento.otec.admin.repositorio.PracticaRepository;
import cl.talento.otec.admin.repositorio.UsuarioRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            UsuarioRepository usuarioRepository,
            CursoRepository cursoRepository,
            EstudianteRepository estudianteRepository,
            PracticaRepository practicaRepository,
            EvaluacionRepository evaluacionRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.findByUsername("admin.primavera").isEmpty()) {
                usuarioRepository.save(new Usuario(
                        "admin.primavera",
                        passwordEncoder.encode("Primavera2026!"),
                        "ROLE_ADMIN",
                        true));
            }

            if (usuarioRepository.findByUsername("estudiante.primavera").isEmpty()) {
                usuarioRepository.save(new Usuario(
                        "estudiante.primavera",
                        passwordEncoder.encode("Primavera2026!"),
                        "ROLE_USER",
                        true));
            }

            if (cursoRepository.count() == 0) {
                Curso javaBasico = new Curso();
                javaBasico.setCodigoInterno("PRI-JAVA-01");
                javaBasico.setCodigo("OTP-2026-JAVA");
                javaBasico.setNombre("Desarrollo Java Spring - Primavera");
                javaBasico.setDuracionHoras(180);
                javaBasico.setCodigoSence("SPR-BOOT-001");
                javaBasico.setCategoria("Programación");
                javaBasico.setActivo(true);
                javaBasico.setArchivado(false);
                cursoRepository.save(javaBasico);

                Curso web = new Curso();
                web.setCodigoInterno("PRI-WEB-01");
                web.setCodigo("OTP-2026-WEB");
                web.setNombre("Desarrollo Web con Spring MVC");
                web.setDuracionHoras(120);
                web.setCodigoSence("SPR-MVC-002");
                web.setCategoria("Desarrollo Web");
                web.setActivo(true);
                web.setArchivado(false);
                cursoRepository.save(web);
            }

            if (estudianteRepository.count() == 0) {
                List<Curso> cursos = cursoRepository.findByActivoTrue();
                if (!cursos.isEmpty()) {
                    Curso cursoBase = cursos.get(0);
                    Curso cursoSecundario = cursos.size() > 1 ? cursos.get(1) : cursoBase;

                    Estudiante camila = new Estudiante();
                    camila.setRut("20.456.789-1");
                    camila.setNombres("Camila");
                    camila.setApellidos("Rojas");
                    camila.setEmail("camila.rojas@primavera.cl");
                    camila.setPrograma("Bootcamp Desarrollo Full Stack Java");
                    camila.setCurso(cursoBase);
                    camila.setActivo(true);
                    estudianteRepository.save(camila);

                    Estudiante martin = new Estudiante();
                    martin.setRut("21.654.987-2");
                    martin.setNombres("Martín");
                    martin.setApellidos("González");
                    martin.setEmail("martin.gonzalez@primavera.cl");
                    martin.setPrograma("Bootcamp Desarrollo Web");
                    martin.setCurso(cursoSecundario);
                    martin.setActivo(true);
                    estudianteRepository.save(martin);

                    if (practicaRepository.count() == 0) {
                        Practica practica1 = new Practica();
                        practica1.setTitulo("Práctica MVC - CRUD de estudiantes");
                        practica1.setDescripcion("Implementar formularios y listado con Spring MVC y Thymeleaf.");
                        practica1.setFechaEntrega(LocalDate.now().plusDays(7));
                        practica1.setEstado("Pendiente");
                        practica1.setEstudiante(camila);
                        practica1.setCurso(cursoBase);
                        practicaRepository.save(practica1);

                        Practica practica2 = new Practica();
                        practica2.setTitulo("Práctica API REST - Cursos");
                        practica2.setDescripcion("Validar operaciones CRUD de cursos y estudiantes desde Postman.");
                        practica2.setFechaEntrega(LocalDate.now().plusDays(10));
                        practica2.setEstado("En progreso");
                        practica2.setEstudiante(martin);
                        practica2.setCurso(cursoSecundario);
                        practicaRepository.save(practica2);
                    }

                    if (evaluacionRepository.count() == 0) {
                        Evaluacion evaluacion1 = new Evaluacion();
                        evaluacion1.setNombre("Evaluación Spring MVC");
                        evaluacion1.setTipo("Sumativa");
                        evaluacion1.setNota(6.4);
                        evaluacion1.setFechaEvaluacion(LocalDate.now().minusDays(2));
                        evaluacion1.setObservacion("Buen dominio de controladores y vistas.");
                        evaluacion1.setEstudiante(camila);
                        evaluacion1.setCurso(cursoBase);
                        evaluacionRepository.save(evaluacion1);

                        Evaluacion evaluacion2 = new Evaluacion();
                        evaluacion2.setNombre("Evaluación APIs RESTful");
                        evaluacion2.setTipo("Formativa");
                        evaluacion2.setNota(5.9);
                        evaluacion2.setFechaEvaluacion(LocalDate.now().minusDays(1));
                        evaluacion2.setObservacion("Debe reforzar el uso de PUT y DELETE.");
                        evaluacion2.setEstudiante(martin);
                        evaluacion2.setCurso(cursoSecundario);
                        evaluacionRepository.save(evaluacion2);
                    }
                }
            }
        };
    }
}
