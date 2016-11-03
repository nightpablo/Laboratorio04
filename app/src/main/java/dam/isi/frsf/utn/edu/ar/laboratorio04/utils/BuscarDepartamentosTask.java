package dam.isi.frsf.utn.edu.ar.laboratorio04.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Ciudad;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Departamento;
import dam.isi.frsf.utn.edu.ar.laboratorio04.utils.BusquedaFinalizadaListener;
import dam.isi.frsf.utn.edu.ar.laboratorio04.utils.FormBusqueda;

/**
 * Created by martdominguez on 22/09/2016.
 */
public class BuscarDepartamentosTask extends AsyncTask<FormBusqueda,Integer,List<Departamento>> {

    private BusquedaFinalizadaListener<Departamento> listener;

    public BuscarDepartamentosTask(BusquedaFinalizadaListener<Departamento> dListener){
        this.listener = dListener;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Departamento> departamentos) {
        listener.busquedaFinalizada(departamentos);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        listener.busquedaActualizada("departamento "+values[0]);


    }

    private void dormir(){
        try {
            Thread.sleep(Long.valueOf(50));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected List<Departamento> doInBackground(FormBusqueda... busqueda) {
        List<Departamento> todos = Departamento.getAlojamientosDisponibles();
        List<Departamento> resultado = new ArrayList<Departamento>();
        int contador = 0;
        int total = todos.size();
        Ciudad ciudadBuscada = busqueda[0].getCiudad();
        double precioMinimo = busqueda[0].getPrecioMinimo();
        double precioMaximo = busqueda[0].getPrecioMaximo();
        boolean permiteFumar = busqueda[0].getPermiteFumar();
        Integer huespedes = busqueda[0].getHuespedes();
        // TODO implementar: buscar todos los departamentos del sistema e ir chequeando las condiciones 1 a 1.
        // si cumplen las condiciones agregarlo a los resultados.
        for(Departamento d: todos){
            // acá hay que ver si el departamento acepta las condiciones que tenemos almacenado en FormBusqueda busqueda
            if(ciudadBuscada.equals(d.getCiudad())
                    && precioMaximo>= d.getPrecio()
                    && precioMinimo<= d.getPrecio()
                    //&& permiteFumar.equals(!d.getNoFumador()) según en Departamento no se implementó el getter para tomar la info del fumador, por lo que omitimos esta condición
                    && huespedes<=d.getCantidadCamas())
            {

                resultado.add(d);
                contador++;
                dormir();
            }
            publishProgress(contador);

        }
        return resultado;
    }
}
