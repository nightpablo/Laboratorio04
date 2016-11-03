package dam.isi.frsf.utn.edu.ar.laboratorio04.modelo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mdominguez on 22/09/16.
 */
public class Usuario implements Serializable {

    private Integer id;
    private String nombre;
    private String correo;
    private String Ringstone;
    private List<Reserva> reservas;
    private Integer puntosSuperPremio;

    public Usuario(String nombre,String correo,String Ringstone){
        this.correo=correo;
        this.nombre = nombre;
        this.Ringstone = Ringstone;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public Integer getPuntosSuperPremio() {
        return puntosSuperPremio;
    }

    public void setPuntosSuperPremio(Integer puntosSuperPremio) {
        this.puntosSuperPremio = puntosSuperPremio;
    }
}
