package dam.isi.frsf.utn.edu.ar.laboratorio04;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Ciudad;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Departamento;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Reserva;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Usuario;
import dam.isi.frsf.utn.edu.ar.laboratorio04.utils.FormBusqueda;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button btnBuscar;
    private Spinner cmbCiudad;
    private ArrayAdapter<Ciudad> adapterCiudad;
    private SeekBar skPrecioMin;
    private TextView tvPrecioMinimo;
    private TextView tvPrecioMaximo;
    private SeekBar skPrecioMax;
    private EditText txtHuespedes;
    private Switch swFumadores;
    private FormBusqueda frmBusq;
    private Button Configuracion;
    public static Usuario usuario;
    private TextView textNombre;
    private TextView textCorreo;
    private TextView textView_Ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = new Usuario("Android Studio","android.studio@android.com",RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.getHeaderView(0);

        textNombre = (TextView) headerLayout.findViewById(R.id.textView_NombreUsuario);
        textCorreo = (TextView) headerLayout.findViewById(R.id.textView_Correo);

        textNombre.setText(usuario.getNombre());
        textCorreo.setText(usuario.getCorreo());

        frmBusq= new FormBusqueda();
        txtHuespedes = (EditText) findViewById(R.id.cantHuespedes);
        skPrecioMin = (SeekBar) findViewById(R.id.precioMin);
        skPrecioMin.setOnSeekBarChangeListener(listenerSB);

        skPrecioMax= (SeekBar) findViewById(R.id.precioMax);
        skPrecioMax.setOnSeekBarChangeListener(listenerSB);

        swFumadores = (Switch) findViewById(R.id.aptoFumadores);
        adapterCiudad = new ArrayAdapter<Ciudad>(MainActivity.this,android.R.layout.simple_spinner_item, Arrays.asList(Ciudad.CIUDADES));

        cmbCiudad = (Spinner) findViewById(R.id.comboCiudad);
        cmbCiudad.setAdapter(adapterCiudad);
        cmbCiudad.setOnItemSelectedListener(comboListener);
        tvPrecioMinimo = (TextView ) findViewById(R.id.txtPrecioMin);
        tvPrecioMaximo= (TextView ) findViewById(R.id.txtPrecioMax);

        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(btnBusarListener);

    }

    private View.OnClickListener btnBusarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(MainActivity.this,ListaDepartamentosActivity.class);
            frmBusq.setPermiteFumar(swFumadores.isSelected());
            i.putExtra("esBusqueda",true);
            i.putExtra("frmBusqueda",frmBusq);
            startActivity(i);
        }
    };

    private AdapterView.OnItemSelectedListener comboListener = new  AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            Ciudad item = (Ciudad) parent.getItemAtPosition(pos);
            frmBusq.setCiudad(item);
            Log.d("MainActivity","ciudad seteada "+item);
        }
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };


    private SeekBar.OnSeekBarChangeListener listenerSB =  new SeekBar.OnSeekBarChangeListener(){

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
        boolean fromUser) {
            if(seekBar.getId()==R.id.precioMin) {
                tvPrecioMinimo.setText("Precio Minimo "+progress);
                frmBusq.setPrecioMinimo(Double.valueOf(progress));
            }
            if(seekBar.getId()==R.id.precioMax) {
                tvPrecioMaximo.setText("Precio Maximo"+progress);
                frmBusq.setPrecioMaximo(Double.valueOf(progress));
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            LayoutInflater inflater = getLayoutInflater();
            final View dialoglayout = inflater.inflate(R.layout.configusuario, null);
            final EditText editText_Usuario = (EditText) dialoglayout.findViewById(R.id.editText_Usuario);
            final EditText editText_Correo = (EditText) dialoglayout.findViewById(R.id.editText_Correo);
            textView_Ringtone = (TextView) dialoglayout.findViewById(R.id.textView_nombreRingtone);
            textView_Ringtone.setText("");
            final Button button_Ringtone = (Button) dialoglayout.findViewById(R.id.button_Ringtone);

            button_Ringtone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Uri currenturi = RingtoneManager.getActualDefaultRingtoneUri(MainActivity.this,RingtoneManager.TYPE_NOTIFICATION);
                    Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select ringtone for notifications:");
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,RingtoneManager.TYPE_ALARM);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currenturi);
                    startActivityForResult( intent, 999);
                }
            });

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setView(dialoglayout);

            builder.setPositiveButton("Configurar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(editText_Usuario.getText().toString().isEmpty())
                        Toast.makeText(MainActivity.this, "Debe ingresar el nombre de usuario", Toast.LENGTH_SHORT).show();
                    else if(editText_Correo.getText().toString().isEmpty())
                        Toast.makeText(MainActivity.this, "Debe ingresar el correo", Toast.LENGTH_SHORT).show();
                    else if(textView_Ringtone.getText().toString().isEmpty())
                        Toast.makeText(MainActivity.this, "Debe seleccionar un ringtone", Toast.LENGTH_SHORT).show();
                    else{
                        usuario.setNombre(editText_Usuario.getText().toString());
                        usuario.setCorreo(editText_Correo.getText().toString());
                        //usuario.setRingstone(editText_Ringtone.getText().toString());

                        textNombre.setText(usuario.getNombre());
                        textCorreo.setText(usuario.getCorreo());
                    }
                }
            });
            builder.setNegativeButton("Cancelar",null);
            builder.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_deptos:
                Intent i1 = new Intent(MainActivity.this,ListaDepartamentosActivity.class);
                i1.putExtra("esBusqueda",false );
                startActivity(i1);
                break;
            case R.id.nav_ofertas:
                break;
            case R.id.nav_perfil:

                break;
            case R.id.nav_reservas:

                if(usuario.getReservas().size()==0){
                    Toast.makeText(MainActivity.this,"No hay reserva",Toast.LENGTH_SHORT).show();
                }
                else{

                    Intent i4 = new Intent(MainActivity.this,AltaReservaActivity.class);
                    i4.putExtra("listaReservas",(ArrayList<Reserva>) usuario.getReservas());
                    i4.putExtra("esReserva",false);
                    startActivity(i4);
                }

                break;
            case R.id.nav_destinos:
                break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 999 && resultCode == RESULT_OK){
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            usuario.setRingstone(uri);
            textView_Ringtone.setText(uri.getPath());
        }
    }

}
