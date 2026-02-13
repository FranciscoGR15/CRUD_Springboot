
package com.digis01.FGutierrezProgramacionNCapasMaven.ML;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario {
    
    private int idUsuario;
    private String userName;
    private String nombre;
    private String apellidoPatero;
    private String apellidoMaterno;
    private String email;
    private String password;
    private Date fechaNacimiento;
    private String sexo;
    private String telefono;
    private String celular;
    private String CURP;
    public com.digis01.FGutierrezProgramacionNCapasMaven.ML.Rol rol;
    public List<com.digis01.FGutierrezProgramacionNCapasMaven.ML.Direccion> direcciones;    
    
    
    public Usuario(){
        this.direcciones = new ArrayList<>();
    }
    
    //GETTER Y SETTER (idUsuario)
    public int getIdUsuario(){
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario){
        this.idUsuario = idUsuario;
    }
    
    
    //GETER Y SETTER (userName)
    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    
    //GETTER Y SETTER (nombre)
    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    
    //GETTER Y SETTER (ApellidoPaterno)
    public String getApellidoPaterno(){
        return apellidoPatero;
    }
    
    public void setApellidoPaterno(String apellidoPaterno){
        this.apellidoPatero = apellidoPaterno;
    }
    
    
    //GETTER Y SETTER (apellidoMaterno)
    public String getApellidoMaterno(){
        return apellidoMaterno;
    }
    
    public void setApellidoMaterno(String apellidoMaterno){
        this.apellidoMaterno = apellidoMaterno;
    }
    
    
    //getter y setter (email)
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    
    //GETTER Y SETTER (password)
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    
    //GETTER Y SETTER (fechaNacimiento)
    public Date getFechaNacimiento(){
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(Date fechaNacimiento){
        this.fechaNacimiento = fechaNacimiento;
    }
    
    
    //GETTER Y SETTER (sexo)
    public String getSexo(){
        return sexo;
    }
    
    public void setSexo(String sexo){
        this.sexo = sexo;
    }
    
    
    //GETTER Y SETTER (telefono)
    public String getTelefono(){
        return telefono;
    }
    
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }
    
    
    //GETTER Y SETTER (celular)
    public String getCelular(){
        return celular;
    }
    
    public void setCelular(String celular){
        this.celular = celular;
    }
    
    
    //GETTER Y SETTER (Curp)
    public String getCURP(){
        return CURP;
    }
    
    public void setCURP(String CURP){
        this.CURP = CURP;
    }
    
}