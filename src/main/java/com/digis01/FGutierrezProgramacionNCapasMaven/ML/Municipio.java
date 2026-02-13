
package com.digis01.FGutierrezProgramacionNCapasMaven.ML;

public class Municipio {
    
    private int idMunicipio;
    private String nombreMunicipio;
    public com.digis01.FGutierrezProgramacionNCapasMaven.ML.Estado estado;
    
    
    //GETTER Y SETTER IdColonia
    public int getIdMunicipio(){
        return idMunicipio;
    }
    
    public void setIdMunicipio(int idMunicipio){
        this.idMunicipio = idMunicipio;
    }
    
    //GETTER Y SETTER Nombre municipio
    public String getNombreMunicipio(){
        return nombreMunicipio;
    }
    
    public void setNombreMunicipio(String nombreMunicipio){
        this.nombreMunicipio = nombreMunicipio;
    }
    
}
