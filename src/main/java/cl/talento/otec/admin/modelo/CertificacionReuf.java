package cl.talento.otec.admin.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "certificaciones_reuf")
public class CertificacionReuf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_certificacion")
    private Integer idCertificacion;

    @Column(name = "numero_certificado")
    private String numeroCertificado;

    @Column(name = "fecha_certificacion")
    private LocalDate fechaCertificacion;

    private String estado;
    private Boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "id_relator")
    private Relator relator;

    public CertificacionReuf() {}

    public Integer getIdCertificacion() { return idCertificacion; }
    public void setIdCertificacion(Integer idCertificacion) { this.idCertificacion = idCertificacion; }

    public String getNumeroCertificado() { return numeroCertificado; }
    public void setNumeroCertificado(String numeroCertificado) { this.numeroCertificado = numeroCertificado; }

    public LocalDate getFechaCertificacion() { return fechaCertificacion; }
    public void setFechaCertificacion(LocalDate fechaCertificacion) { this.fechaCertificacion = fechaCertificacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public Relator getRelator() { return relator; }
    public void setRelator(Relator relator) { this.relator = relator; }
}
