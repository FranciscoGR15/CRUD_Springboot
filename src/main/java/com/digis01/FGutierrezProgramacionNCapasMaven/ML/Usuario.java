package com.digis01.FGutierrezProgramacionNCapasMaven.ML;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario {

    private int idUsuario;
    private String imagen;
    @NotBlank(message = "El username es obligatorio")
    @Size(min = 3, max = 50, message = "Debe tener entre 3 y 50 caracteres")
    private String userName;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 50, message = "Debe tener entre 3 y 50 caracteres")
    private String nombre;
    @NotBlank(message = "El apellido paterno es obligatorio")
    @Size(min = 3, max = 50, message = "Debe tener entre 3 y 50 caracteres")
    private String apellidoPaterno;
    @Size(min = 3, max = 50, message = "Debe tener entre 3 y 50 caracteres")
    private String apellidoMaterno;
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email inválido")
    private String email;
    @NotBlank(message = "El password es obligatorio")
    @Size(min = 3, max = 50, message = "Debe tener entre 3 y 50 caracteres")
    private String password;
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;
    private String sexo;
    @Size(min = 8, max = 20, message = "Debe tener entre 8 y 20 caracteres")
    @NotBlank(message = "El telefono es obligatorio")
    private String telefono;
    private String celular;
    @Size(min = 10, max = 50, message = "Debe tener entre 10 y 50 caracteres")
    private String CURP;
    public Rol rol;
    public List<Direccion> direcciones;

    public Usuario() {
        this.direcciones = new ArrayList<>();
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    //GETTER Y SETTER (idUsuario)
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    //GETER Y SETTER (userName)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    //GETTER Y SETTER (nombre)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //GETTER Y SETTER (ApellidoPaterno)
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    //GETTER Y SETTER (apellidoMaterno)
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    //getter y setter (email)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //GETTER Y SETTER (password)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //GETTER Y SETTER (fechaNacimiento)
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    //GETTER Y SETTER (sexo)
    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    //GETTER Y SETTER (telefono)
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    //GETTER Y SETTER (celular)
    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    //GETTER Y SETTER (Curp)
    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

}
