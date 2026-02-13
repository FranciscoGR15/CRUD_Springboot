
package com.digis01.FGutierrezProgramacionNCapasMaven.ML;

public class Direccion {
    
    private int idDireccion;
    private String calle;
    private String numeroInterior;
    private String numeroExterior;
    public com.digis01.FGutierrezProgramacionNCapasMaven.ML.Usuario usuario;
    public com.digis01.FGutierrezProgramacionNCapasMaven.ML.Colonia colonia;
    
    //GETTER Y SETTER IdDIreccion
    public int getIdDireccion(){
        return idDireccion;
    }
    
    public void setIdDireccion(int idDireccion){
        this.idDireccion = idDireccion;
    }
    
    
    //GETTER Y SETTER CALLE
    public String getCalle(){
        return calle;
    }
    
    public void setCalle(String calle){
        this.calle = calle;
    }
    
    
    //GETTER Y SETTER NUMERO INTERIOR
    public String getNumeroInterior(){
        return numeroInterior;
    }
    
    public void setNumeroInterior(String numeroInterior){
        this.numeroInterior = numeroInterior;
    }
    
    
    //GETTER Y SETTER NUMERO EXTERIOR
    public String getNumeroExterior(){
        return numeroExterior;
    }
    
    public void setNumeroExterior(String numeroExterior){
        this.numeroExterior = numeroExterior;
    }
    
    
}
