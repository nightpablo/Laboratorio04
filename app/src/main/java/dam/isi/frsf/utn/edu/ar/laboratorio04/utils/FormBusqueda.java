package dam.isi.frsf.utn.edu.ar.laboratorio04.utils;

import java.io.Serializable;

import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Ciudad;

/**
 * Created by martdominguez on 22/09/2016.
 */
public class FormBusqueda implements Serializable {
    private Double precioMinimo;
    private Double precioMaximo;
    private Ciudad ciudad;
    private Boolean permiteFumar;
    private Integer huespedes;

    public FormBusqueda(){this(0.0,0.0,new Ciudad(),false,0);} //Agregué todas las iniciales de tal manera que se pueda evaluar las condiciones, sino se saltará error y se cerrará la aplicación

    public FormBusqueda(Double precioMinimo, Double precioMaximo) {
        this(precioMinimo,precioMaximo,null,null,null);
    }

    public FormBusqueda(Ciudad ciudad) {
        this(null,null,ciudad,null,null);
    }

    public FormBusqueda(Double precioMinimo, Double precioMaximo, Ciudad ciudad, Boolean permiteFumar, Integer huespedes) {
        this.precioMinimo = precioMinimo;
        this.precioMaximo = precioMaximo;
        this.ciudad = ciudad;
        this.permiteFumar = permiteFumar;
        this.huespedes = huespedes;
    }

    public Double getPrecioMinimo() {
        return precioMinimo;
    }

    public void setPrecioMinimo(Double precioMinimo) {
        this.precioMinimo = precioMinimo;
    }

    public Double getPrecioMaximo() {
        return precioMaximo;
    }

    public void setPrecioMaximo(Double precioMaximo) {
        this.precioMaximo = precioMaximo;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Boolean getPermiteFumar() {
        return permiteFumar;
    }

    public void setPermiteFumar(Boolean permiteFumar) {
        this.permiteFumar = permiteFumar;
    }

    public Integer getHuespedes() {
        return huespedes;
    }

    public void setHuespedes(Integer huespedes) {
        this.huespedes = huespedes;
    }
}
