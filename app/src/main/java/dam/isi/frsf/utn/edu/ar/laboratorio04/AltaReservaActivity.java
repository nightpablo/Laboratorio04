package dam.isi.frsf.utn.edu.ar.laboratorio04;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ServiceConfigurationError;

import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Departamento;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Reserva;
import dam.isi.frsf.utn.edu.ar.laboratorio04.utils.Utils;

public class AltaReservaActivity extends AppCompatActivity {

    private ListView listaReserva;
    private ReservaAdapter adaptadorReserva;
    private Button botonReservar;
    private Boolean SeReserva;
    public static Reserva Seleccionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_reserva);
        listaReserva = (ListView) findViewById(R.id.ListView_ListaReserva);
        botonReservar = (Button) findViewById(R.id.Button_Reservar);


    }

    @Override
    protected void onStart() {
        super.onStart();
        final Intent intent = getIntent();
        Boolean esReserva = intent.getExtras().getBoolean("esReserva");
        botonReservar.setVisibility(View.INVISIBLE);
        if(esReserva) {
            ArrayList<Reserva> dptoElegido = (ArrayList<Reserva>) intent.getSerializableExtra("listaReservas");
            adaptadorReserva = new ReservaAdapter(AltaReservaActivity.this, dptoElegido);
            listaReserva.setAdapter(adaptadorReserva);
            listaReserva.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    view.setSelected(true);
                    for (int i = 0; i < listaReserva.getChildCount(); i++)
                        listaReserva.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    listaReserva.getChildAt(position).setBackgroundColor(Color.CYAN);
                    Seleccionado = (Reserva) listaReserva.getItemAtPosition(position);

                }
            });
            botonReservar.setVisibility(View.VISIBLE);
            botonReservar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SeReserva=false;
                    if(Seleccionado==null){
                        Toast.makeText(AltaReservaActivity.this,"¡Debe Seleccionar una reserva!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    muestraDialogo();

                }
            });
        }
        else{
            ArrayList<Reserva> dptoElegido = (ArrayList<Reserva>) intent.getSerializableExtra("listaReservas");
            adaptadorReserva = new ReservaAdapter(AltaReservaActivity.this, dptoElegido);
            listaReserva.setAdapter(adaptadorReserva);
        }
    }

    private void muestraDialogo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("¿Seguro que deseas reservar?:\nid: "+Seleccionado.getId()+"\n" +
                "precio: "+Seleccionado.getAlojamiento().getPrecio()+"\nDescripción: "+Seleccionado.getAlojamiento().getDescripcion()+"\nCiudad: "+Seleccionado.getAlojamiento().getCiudad()+"\nFecha Inicio: "+Seleccionado.getFechaInicio()+". Fecha Fin: "+Seleccionado.getFechaFin())
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Aquí lo que deseas realizar
                        Toast Reservado = Toast.makeText(AltaReservaActivity.this,"La reserva está en pendiente: "+Seleccionado.getId()+"\n"+Seleccionado.getConfirmada().toString()+"\n"+Seleccionado.getFechaInicio().toString(),Toast.LENGTH_SHORT);
                        Reservado.show();

                        TestReceiver activarAlarma = new TestReceiver();
                        activarAlarma.sendRepeatingAlarm(AltaReservaActivity.this);

                        //sendRepeatingAlarm();
                        finish();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

    }




}
