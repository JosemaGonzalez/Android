package com.example.josema.aceitunas2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Chronometer tiempo;
    TextView puntuacion;
    TableLayout tabla;

    private int[] cartas;
    int c;
    //array de las imagenes
    int[] imagenes = {R.drawable.arbequina, R.drawable.blanqueta, R.drawable.cornicabra,
            R.drawable.empeltre, R.drawable.hojiblanca, R.drawable.lechindesevilla,
            R.drawable.mancanillacacerena, R.drawable.picual, R.drawable.picudo,
            R.drawable.verdialdebadajoz, R.drawable.verdialdevelez, R.drawable.olivo, R.drawable.olivo,
            R.drawable.olivo, R.drawable.olivo, R.drawable.olivo, R.drawable.olivo,
            R.drawable.olivo, R.drawable.olivo, R.drawable.olivo, R.drawable.olivo,
            R.drawable.olivo, R.drawable.olivo, R.drawable.olivo
    };
    //array de los imagebuton
    int[] iv = {R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4, R.id.iv5, R.id.iv6, R.id.iv7, R.id.iv8, R.id.iv9,
            R.id.iv10, R.id.iv11, R.id.iv12, R.id.iv13, R.id.iv14, R.id.iv15, R.id.iv16, R.id.iv17, R.id.iv18,
            R.id.iv19, R.id.iv20, R.id.iv21, R.id.iv22, R.id.iv23, R.id.iv24};
    private int puntos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tiempo = (Chronometer) findViewById(R.id.tiempo);
        tiempo.stop();
        tabla = (TableLayout) findViewById(R.id.tabla);
        tabla.setVisibility(View.INVISIBLE);
        puntuacion = (TextView) findViewById(R.id.texNumeroPuntuacion);
        cartas = mezclar(imagenes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //switch para realizar las diferentes llamadas a las acciones
        switch (id) {
            case R.id.action_new:
                iniciar();
                return true;
            case R.id.action_help:
                Toast.makeText(getApplicationContext(), "Instrucciones", Toast.LENGTH_SHORT).show();
                crearDialogo();
                return true;
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "Configuracion", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_exit:
                Toast.makeText(getApplicationContext(), "Salir", Toast.LENGTH_SHORT).show();
                System.exit(0);
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }
    }

    //metodo que inicia la aplicacion
    private void iniciar() {
        Toast.makeText(getApplicationContext(), "Juego nuevo", Toast.LENGTH_SHORT).show();
        tiempo.setText("00:00");
        tiempo.start();
        //pone el crono a 0
        tiempo.setBase(SystemClock.elapsedRealtime());
        tabla.setVisibility(View.VISIBLE);
        //mezcla el array de imagenes
        mezclar(imagenes);
        puntuacion.setText("0");
        //pone en los imagebuton la imagen de interrogacion
        asignarCartasInterr();
        puntos = 0;
        c = 0;
        //pone la puntuacion a 0
        cambiarPuntuacion(puntos);
    }

    //metodo para poner los imagebuton una interrogacion
    public void asignarCartasInterr() {
        int valor;
        TableLayout tabla;
        TableRow fila;
        tabla = (TableLayout) findViewById(R.id.tabla);
        //pone los imagebuton para que se puedan pulsar
        for (int i = 0; i < iv.length; i++) {
            ((ImageView) findViewById(iv[i])).setEnabled(true);
        }
        for (int i = 0; i < tabla.getChildCount(); i++) {
            valor = tabla.getChildAt(i).getId();
            fila = (TableRow) findViewById(valor);
            for (int j = 0; j < fila.getChildCount(); j++) {
                valor = fila.getChildAt(j).getId();
                //asigna la imagen a cada immagebuton
                ((ImageView) findViewById(valor)).setImageResource(R.drawable.pregunta);
            }
        }

    }

    //metodo que desordena las imagenes
    public int[] mezclar(int[] matriz) {

        int tamaño = matriz.length;
        int Aleatorio, aux;

        for (int i = 0; i < tamaño; i++) {
            Aleatorio = (int) (Math.random() * tamaño - 1);
            aux = matriz[i];
            matriz[i] = matriz[Aleatorio];
            matriz[Aleatorio] = aux;
        }

        return matriz;
    }

    //metodo que cambia y refresca los puntos
    public void cambiarPuntuacion(int puntos) {
        TextView texto;
        texto = (TextView) (findViewById(R.id.texNumeroPuntuacion));
        texto.setText(String.valueOf(puntos));
    }

    //metodo que se usa al pulsar cada imagebuton
    public void pulsar(View v) {
        int iNumeroBotonPulsado = 0;
        ImageView im = (ImageView) v;
        //pone el boton para que no se pulse dos veces
        v.setEnabled(false);
        for (int i = 0; i < imagenes.length; i++) {
            if (v.getId() == iv[i]) {
                iNumeroBotonPulsado = i;
                break;
            }
        }

        im.setImageResource(imagenes[iNumeroBotonPulsado]);
        im.setTag(imagenes[iNumeroBotonPulsado]);
        //condicion para cuando se falla reste puntos
        if (cartas[iNumeroBotonPulsado] == R.drawable.olivo) {
            puntos = puntos - 5;
            Toast.makeText(getApplicationContext(), "Inténtalo otra vez", Toast.LENGTH_SHORT).show();
            cambiarPuntuacion(puntos);
        }
        //demas condiciones cuando acierta sume puntos y dependiendo del tipo muestre un mensaje diferente
        //tiene un contador para cuando encuentre todas pare el tiempo y muestre un dialogo con la puntuacion final
        if (cartas[iNumeroBotonPulsado] == R.drawable.arbequina) {
            puntos = puntos + 20;
            c++;
            Toast.makeText(getApplicationContext(), "Aceituna Arbequina", Toast.LENGTH_SHORT).show();
            if (c == 11) {
                tiempo.stop();
                cambiarPuntuacion(puntos);
                crearDialogo2(puntos);
            }
            cambiarPuntuacion(puntos);

        }
        if (cartas[iNumeroBotonPulsado] == R.drawable.blanqueta) {
            puntos = puntos + 20;
            c++;
            Toast.makeText(getApplicationContext(), "Aceituna Blanqueta", Toast.LENGTH_SHORT).show();
            if (c == 11) {
                tiempo.stop();
                cambiarPuntuacion(puntos);
                crearDialogo2(puntos);
            }
            cambiarPuntuacion(puntos);

        }
        if (cartas[iNumeroBotonPulsado] == R.drawable.cornicabra) {
            puntos = puntos + 20;
            c++;
            Toast.makeText(getApplicationContext(), "Aceituna Cornicabra", Toast.LENGTH_SHORT).show();
            if (c == 11) {
                tiempo.stop();
                cambiarPuntuacion(puntos);
                crearDialogo2(puntos);

            }
            cambiarPuntuacion(puntos);

        }
        if (cartas[iNumeroBotonPulsado] == R.drawable.empeltre) {
            puntos = puntos + 20;
            c++;
            Toast.makeText(getApplicationContext(), "Aceituna Empeltre", Toast.LENGTH_SHORT).show();
            if (c == 11) {
                tiempo.stop();
                cambiarPuntuacion(puntos);
                crearDialogo2(puntos);

            }
            cambiarPuntuacion(puntos);
        }
        if (cartas[iNumeroBotonPulsado] == R.drawable.hojiblanca) {
            puntos = puntos + 20;
            c++;
            Toast.makeText(getApplicationContext(), "Aceituna Hojiblanca", Toast.LENGTH_SHORT).show();
            if (c == 11) {
                tiempo.stop();
                cambiarPuntuacion(puntos);
                crearDialogo2(puntos);


            }
            cambiarPuntuacion(puntos);

        }
        if (cartas[iNumeroBotonPulsado] == R.drawable.lechindesevilla) {
            puntos = puntos + 20;
            c++;
            Toast.makeText(getApplicationContext(), "Aceituna Lechin de Sevilla", Toast.LENGTH_SHORT).show();
            if (c == 11) {
                tiempo.stop();
                cambiarPuntuacion(puntos);
                crearDialogo2(puntos);

            }
            cambiarPuntuacion(puntos);

        }
        if (cartas[iNumeroBotonPulsado] == R.drawable.mancanillacacerena) {
            puntos = puntos + 20;
            c++;
            Toast.makeText(getApplicationContext(), "Aceituna Mazanilla Cacereña", Toast.LENGTH_SHORT).show();
            if (c == 11) {
                tiempo.stop();
                cambiarPuntuacion(puntos);
                crearDialogo2(puntos);

            }
            cambiarPuntuacion(puntos);

        }
        if (cartas[iNumeroBotonPulsado] == R.drawable.picual) {
            puntos = puntos + 20;
            c++;
            Toast.makeText(getApplicationContext(), "Aceituna Picual", Toast.LENGTH_SHORT).show();
            if (c == 11) {
                tiempo.stop();
                cambiarPuntuacion(puntos);
                crearDialogo2(puntos);

            }
            cambiarPuntuacion(puntos);

        }
        if (cartas[iNumeroBotonPulsado] == R.drawable.picudo) {
            puntos = puntos + 20;
            c++;
            Toast.makeText(getApplicationContext(), "Aceituna Picudo", Toast.LENGTH_SHORT).show();
            if (c == 11) {
                tiempo.stop();
                cambiarPuntuacion(puntos);
                crearDialogo2(puntos);

            }
            cambiarPuntuacion(puntos);

        }
        if (cartas[iNumeroBotonPulsado] == R.drawable.verdialdebadajoz) {
            puntos = puntos + 20;
            c++;
            Toast.makeText(getApplicationContext(), "Aceituna Verdial de Badajoz", Toast.LENGTH_SHORT).show();
            if (c == 11) {
                tiempo.stop();
                cambiarPuntuacion(puntos);
                crearDialogo2(puntos);

            }
            cambiarPuntuacion(puntos);

        }
        if (cartas[iNumeroBotonPulsado] == R.drawable.verdialdevelez) {
            puntos = puntos + 20;
            c++;
            Toast.makeText(getApplicationContext(), "Aceituna Verdial de Velez", Toast.LENGTH_SHORT).show();
            if (c == 11) {
                tiempo.stop();
                cambiarPuntuacion(puntos);
                crearDialogo2(puntos);

            }
            cambiarPuntuacion(puntos);

        }
    }

    //metodo para configurar los parametros, no esta implementado en la aplicacion final
    public void crearConfiguracion() {
        Intent i = new Intent(this, Configuracion.class);
        startActivity(i);
    }

    //dialogo para mostrar las instrucciones
    public void crearDialogo() {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        Context mcontext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.instrucciones, (ViewGroup) findViewById(R.id.dialogo2));
        builder.setTitle("Instrucciones");
        builder.setView(layout);
        builder.setMessage("Aplicacion Buscaminas Aceitunas\n\n\n Nuevo: Inicia juego nuevo\n Configurar: Configura los parámetros del juego\n Intrucciones: Información del juego\n Salir: Termina el juego")

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create();
        builder.show();
    }

    //dialogo para mostrar la puntuacion final
    public void crearDialogo2(int puntuacion) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        Context mcontext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.instrucciones, (ViewGroup) findViewById(R.id.dialogo2));
        builder.setTitle("Final");
        builder.setView(layout);
        builder.setMessage("Has ganado\n\n Puntuación: " + puntuacion)

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create();
        builder.show();
    }
}