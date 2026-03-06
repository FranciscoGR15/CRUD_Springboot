
package com.digis01.FGutierrezProgramacionNCapasMaven.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Estado {
    
    @Id
    @Column(name = "idestado")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEstado;
    @Column(name = "nombreestado")
    private String nombreEstado;
    @ManyToOne
    @JoinColumn(name = "idpais")
    public Pais pais;
    
    
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

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }  
}
