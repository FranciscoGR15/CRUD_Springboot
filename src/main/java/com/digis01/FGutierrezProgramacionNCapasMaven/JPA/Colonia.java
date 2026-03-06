package com.digis01.FGutierrezProgramacionNCapasMaven.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Colonia {

    @Id
    @Column(name = "idcolonia")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idColonia;
    @Column(name = "nombrecolonia")
    private String nombreColonia;
    @Column(name = "codigopostal")
    private String codigoPostal;
    @ManyToOne
    @JoinColumn(name = "idmunicipio")
    public Municipio municipio;

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
