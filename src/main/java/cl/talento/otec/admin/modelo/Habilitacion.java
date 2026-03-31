package cl.talento.otec.admin.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "habilitaciones")
public class Habilitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_habilitacion")
    private Integer idHabilitacion;

    @Column(name = "area_curso")
    private String areaCurso;

    @Column(name = "codigo_especialidad_sence")
    @NotBlank(message = "Código de especialidad SENCE es obligatorio")
    private String codigoEspecialidadSence;

    @Column(name = "estado_reuf")
    @NotBlank(message = "Estado REUF es obligatorio")
    private String estadoReuf;

    @Column(name = "fecha_vencimiento_reuf")
    @NotNull(message = "Fecha de vencimiento REUF es obligatoria")
    private LocalDate fechaVencimientoReuf;

    private String estado;
    private Boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "id_relator")
    private Relator relator;

    public Habilitacion() {}

    public Integer getIdHabilitacion() { return idHabilitacion; }
    public void setIdHabilitacion(Integer idHabilitacion) { this.idHabilitacion = idHabilitacion; }

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

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public Relator getRelator() { return relator; }
    public void setRelator(Relator relator) { this.relator = relator; }
}
