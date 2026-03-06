
package com.digis01.FGutierrezProgramacionNCapasMaven.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Rol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrol")
    private int idRol;
    @Column(name = "nombrerol")
    private String nombreRol;
    
    
    //GETTER Y SETTER (Rol)
    public int getIdRol(){
        return idRol;
    }
    
    public void setIdRol(int idRol){
        this.idRol = idRol;
    }
    
    
    //GETTER Y SETTER (Nombre Rol)
    public String getNombreRol(){
        return nombreRol;
    }
    
    public void setNombreRol(String nombreRol){
        this.nombreRol = nombreRol;
    }
    
}
