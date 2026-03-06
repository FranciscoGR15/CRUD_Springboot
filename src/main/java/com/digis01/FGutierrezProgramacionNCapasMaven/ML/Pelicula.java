package com.digis01.FGutierrezProgramacionNCapasMaven.ML;

import java.util.List;

public class Pelicula {

    private String titulo;
    private int anio;
    private List<Actor> actores;

    public Pelicula() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public List<Actor> getActores() {
        return actores;
    }

    public void setActores(List<Actor> actores) {
        this.actores = actores;
    }

}
