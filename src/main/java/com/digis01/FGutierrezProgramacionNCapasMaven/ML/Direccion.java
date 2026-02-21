package com.digis01.FGutierrezProgramacionNCapasMaven.ML;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Direccion {

    private int idDireccion;
    @NotBlank(message = "No deje el campo vacio")
    @Size(min = 2, max = 50, message = "La calle debe contener de 3 a 5 caracteres")
    private String calle;
    private String numeroInterior;
    @NotBlank(message = "No deje este campo vacio")
    private String numeroExterior;
    public com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario usuario;
    public com.digis01.FGutierrezProgramacionNCapasMaven.ML.Colonia colonia;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Colonia getColonia() {
        return colonia;
    }

    public void setColonia(Colonia colonia) {
        this.colonia = colonia;
    }

    //GETTER Y SETTER IdDIreccion
    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    //GETTER Y SETTER CALLE
    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    //GETTER Y SETTER NUMERO INTERIOR
    public String getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    //GETTER Y SETTER NUMERO EXTERIOR
    public String getNumeroExterior() {
        return numeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

}
