package com.digis01.FGutierrezProgramacionNCapasMaven.ML;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Colonia {

    private int idColonia;
    private String nombreColonia;
    @NotBlank(message = "Escriba su codigo postal")
    @Size(min = 3, max = 10, message = "Debe contener de 3 a 10 caracteres")
    private String codigoPostal;
    public com.digis01.FGutierrezProgramacionNCapasMaven.ML.Municipio municipio;

    //GETTER Y SETTER idColonia
    public int getIdColonia() {
        return idColonia;
    }

    public void setIdColonia(int idColonia) {
        this.idColonia = idColonia;
    }

    //GETTER Y SETTER NOMBRE COLONIA
    public String getNombreColonia() {
        return nombreColonia;
    }

    public void setNombreColonia(String nombreColonia) {
        this.nombreColonia = nombreColonia;
    }

    //GETTER Y SETTER CODIGO POSTAL
    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

}
