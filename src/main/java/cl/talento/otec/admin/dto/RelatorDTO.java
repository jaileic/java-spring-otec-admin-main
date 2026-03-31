package cl.talento.otec.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RelatorDTO {

    private Integer idRelator;

    @NotBlank(message = "RUT es obligatorio")
    private String rut;

    @NotBlank(message = "Nombres son obligatorios")
    private String nombres;

    @NotBlank(message = "Apellidos son obligatorios")
    private String apellidos;

    @Email(message = "Email debe ser válido")
    @NotBlank(message = "Email es obligatorio")
    private String email;

    private String profesionTitulo;

    private Boolean activo = true;

    public RelatorDTO() {}

    public RelatorDTO(Integer idRelator, String rut, String nombres, String apellidos, String email, String profesionTitulo) {
        this.idRelator = idRelator;
        this.rut = rut;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.profesionTitulo = profesionTitulo;
    }

    public Integer getIdRelator() { return idRelator; }
    public void setIdRelator(Integer idRelator) { this.idRelator = idRelator; }

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getProfesionTitulo() { return profesionTitulo; }
    public void setProfesionTitulo(String profesionTitulo) { this.profesionTitulo = profesionTitulo; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
