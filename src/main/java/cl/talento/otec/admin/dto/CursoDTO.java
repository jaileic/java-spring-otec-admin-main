package cl.talento.otec.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

public class CursoDTO {
    private Integer idCurso;

    @NotBlank(message = "El código interno es obligatorio")
    private String codigoInterno;

    @NotBlank(message = "El código es obligatorio")
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private Integer idRelator;

    private String nombreRelator;

    @NotNull(message = "La duración es obligatoria")
    @Min(value = 1, message = "La duración debe ser mayor a 0")
    private Integer duracionHoras;

    @NotBlank(message = "El código SENCE es obligatorio")
    private String codigoSence;

    private String categoria;

    private Boolean activo;

    private Boolean archivado;

    public CursoDTO() {
    }

    public CursoDTO(Integer idCurso, String codigoInterno, String codigo, String nombre, Integer idRelator,
            String nombreRelator, Integer duracionHoras, String codigoSence, String categoria, Boolean activo,
            Boolean archivado) {
        this.idCurso = idCurso;
        this.codigoInterno = codigoInterno;
        this.codigo = codigo;
        this.nombre = nombre;
        this.idRelator = idRelator;
        this.nombreRelator = nombreRelator;
        this.duracionHoras = duracionHoras;
        this.codigoSence = codigoSence;
        this.categoria = categoria;
        this.activo = activo;
        this.archivado = archivado;
    }

    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdRelator() {
        return idRelator;
    }

    public void setIdRelator(Integer idRelator) {
        this.idRelator = idRelator;
    }

    public String getNombreRelator() {
        return nombreRelator;
    }

    public void setNombreRelator(String nombreRelator) {
        this.nombreRelator = nombreRelator;
    }

    public Integer getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(Integer duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    public String getCodigoSence() {
        return codigoSence;
    }

    public void setCodigoSence(String codigoSence) {
        this.codigoSence = codigoSence;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Boolean getArchivado() {
        return archivado;
    }

    public void setArchivado(Boolean archivado) {
        this.archivado = archivado;
    }
}
