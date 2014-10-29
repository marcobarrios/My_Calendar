package com.dsoft.mycalendar.Notes;

/**
 * Created by Marco Barrios on 26/10/2014.
 */
public class Anotacion {

    private String titulo;
    private String descripcion;
    private String fecha;

    public Anotacion(String titulo, String descripcion, String fecha) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public Anotacion() {
    }

    //<editor-fold desc="GETTERS">
    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFecha() {
        return fecha;
    }
    //</editor-fold>

    //<editor-fold desc="SETTERS">
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    //</editor-fold>

    //<editor-fold desc="EQUALS & HASHCODE">
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Anotacion anotacion = (Anotacion) o;

        if (!descripcion.equals(anotacion.descripcion)) return false;
        if (!fecha.equals(anotacion.fecha)) return false;
        if (!titulo.equals(anotacion.titulo)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = titulo.hashCode();
        result = 31 * result + descripcion.hashCode();
        result = 31 * result + fecha.hashCode();
        return result;
    }
    //</editor-fold>
}
