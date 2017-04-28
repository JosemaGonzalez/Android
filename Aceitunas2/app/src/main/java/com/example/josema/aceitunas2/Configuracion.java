package com.example.josema.aceitunas2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;import com.example.josema.aceitunas2.R;import java.lang.Override;import java.lang.String;

/**
 * Created by Josema on 04/12/2015.
 */
public class Configuracion extends AppCompatActivity implements Spinner.OnItemSelectedListener{
    Spinner scol,sfil;
    public void onCreate(Bundle savedInstanceState) {
        String [] elementos = {"4","6","8","10"};
        ArrayAdapter<String> adaptador;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracion_main);
        scol = (Spinner) findViewById(R.id.spinnerColumnas);
        sfil = (Spinner) findViewById(R.id.spinnerFilas);
        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,elementos);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scol.setAdapter(adaptador);
        scol.setOnItemSelectedListener(this);
        sfil.setAdapter(adaptador);
        sfil.setOnItemSelectedListener(this);
    }

    public void click(){
        Toast.makeText(Configuracion.this,"Has seleccionado "+ scol.getSelectedItem().toString()
                + " columnas y " +sfil.getSelectedItem().toString() + " filas", Toast.LENGTH_SHORT)
                .show();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
