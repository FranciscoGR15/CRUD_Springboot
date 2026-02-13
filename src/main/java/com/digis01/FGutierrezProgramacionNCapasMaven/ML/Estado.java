
package com.digis01.FGutierrezProgramacionNCapasMaven.ML;

public class Estado {
    
    private int idEstado;
    private String nombreEstado;
    public com.digis01.FGutierrezProgramacionNCapasMaven.ML.Pais pais;
    
    
    //GETTER Y SETTER idEstado
    public int getIdEstado(){
        return idEstado;
    }
    
    public void setIdEstado(int idEstado){
        this.idEstado = idEstado;
    }
    
    
    // GETTER Y SETTER Nombre estado
    public String getNombreEstado(){
        return nombreEstado;
    }
    
    public void setNombreEstado(String nombreEstado){
        this.nombreEstado = nombreEstado;
    }
    
}
