
package com.digis01.FGutierrezProgramacionNCapasMaven.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Pais {
    
    @Id
    @Column(name = "idpais")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPais;
    @Column(name = "nombrepais")
    private String nombrePais;
    
    //GETTER Y SETTER idPais
    public int getIdPais(){
        return idPais;
    }
    
    public void setIdPais(int idPais){
        this.idPais = idPais;
    }
    
    
    public String getNombrePais(){
        return nombrePais;
    }
    
    public void setNombrePais(String nombrePais){
        this.nombrePais = nombrePais;
    }
    
}