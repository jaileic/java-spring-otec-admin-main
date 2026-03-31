package cl.talento.otec.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class HabilitacionDTO {

    private Integer idHabilitacion;
    private Integer idRelator;

    @NotBlank(message = "El área del curso es obligatoria")
    private String areaCurso;

    @NotBlank(message = "Código de especialidad SENCE es obligatorio")
    private String codigoEspecialidadSence;

    @NotBlank(message = "Estado REUF es obligatorio")
    private String estadoReuf;

    @NotNull(message = "Fecha de vencimiento REUF es obligatoria")
    private LocalDate fechaVencimientoReuf;

    private String estado;

    public HabilitacionDTO() {}

    public HabilitacionDTO(Integer idHabilitacion, Integer idRelator, String areaCurso, 
                          String codigoEspecialidadSence, String estadoReuf, LocalDate fechaVencimientoReuf, String estado) {
        this.idHabilitacion = idHabilitacion;
        this.idRelator = idRelator;
        this.areaCurso = areaCurso;
        this.codigoEspecialidadSence = codigoEspecialidadSence;
        this.estadoReuf = estadoReuf;
        this.fechaVencimientoReuf = fechaVencimientoReuf;
        this.estado = estado;
    }

    public Integer getIdHabilitacion() { return idHabilitacion; }
    public void setIdHabilitacion(Integer idHabilitacion) { this.idHabilitacion = idHabilitacion; }

    public Integer getIdRelator() { return idRelator; }
    public void setIdRelator(Integer idRelator) { this.idRelator = idRelator; }

    public String getAreaCurso() { return areaCurso; }
    public void setAreaCurso(String areaCurso) { this.areaCurso = areaCurso; }

    public String getCodigoEspecialidadSence() { return codigoEspecialidadSence; }
    public void setCodigoEspecialidadSence(String codigoEspecialidadSence) { this.codigoEspecialidadSence = codigoEspecialidadSence; }

    public String getEstadoReuf() { return estadoReuf; }
    public void setEstadoReuf(String estadoReuf) { this.estadoReuf = estadoReuf; }

    public LocalDate getFechaVencimientoReuf() { return fechaVencimientoReuf; }
    public void setFechaVencimientoReuf(LocalDate fechaVencimientoReuf) { this.fechaVencimientoReuf = fechaVencimientoReuf; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
