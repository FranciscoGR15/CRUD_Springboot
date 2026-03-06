package com.digis01.FGutierrezProgramacionNCapasMaven.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Municipio {

    @Id
    @Column(name = "idmunicipio")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMunicipio;
    @Column(name = "nombremunicipio")
    private String nombreMunicipio;
    @ManyToOne
    @JoinColumn(name = "idestado")
    public Estado estado;

    //GETTER Y SETTER IdColonia
    public int getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    //GETTER Y SETTER Nombre municipio
    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

}