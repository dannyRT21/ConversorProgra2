package com.ugb.conversorprogra2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TabHost tbh;
    Button btn;
    TextView tempVal;
    Spinner spn;
    conversores objConversores = new conversores();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbh = findViewById(R.id.tbhConversor);
        tbh.setup();

        tbh.addTab(tbh.newTabSpec("Monedas").setContent(R.id.tabMonedas).setIndicator("MONEDAS", null));
        tbh.addTab(tbh.newTabSpec("Longitud").setContent(R.id.tabLongitud).setIndicator("LONGITUD", null));
        tbh.addTab(tbh.newTabSpec("Tiempo").setContent(R.id.tabTiempo).setIndicator("TIEMPO", null));
        tbh.addTab(tbh.newTabSpec("Almacenamiento").setContent(R.id.tabAlmacenamiento).setIndicator("ALMACENAMIENTO", null));
        tbh.addTab(tbh.newTabSpec("Transferenciadedatos").setContent(R.id.tabAlmacenamiento).setIndicator("TRANSFERENCIA DE DATOS", null));

        btn = findViewById(R.id.btnCalcular);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempVal = findViewById(R.id.txtCantidad);
                String cantidadTexto = tempVal.getText().toString();

                // Validación para verificar si el campo está vacío
                if (cantidadTexto.isEmpty()) {
                    Toast.makeText(MainActivity.this, "INGRESA UN VALOR", Toast.LENGTH_SHORT).show();
                    return; // Detener la ejecución si el campo está vacío
                }

                int opcion = tbh.getCurrentTab(); // Obtener la pestaña actual
                double cantidad = Double.parseDouble(cantidadTexto);

                double respuesta = 0;
                String unidad = "";

                switch (opcion) {
                    case 0: // Monedas
                        int deMonedas = ((Spinner) findViewById(R.id.spnDeMonedas)).getSelectedItemPosition();
                        int aMonedas = ((Spinner) findViewById(R.id.spnAMonedas)).getSelectedItemPosition();

                        // Validación para evitar conversiones de la misma unidad
                        if (deMonedas == aMonedas) {
                            Toast.makeText(MainActivity.this, "No se pueden convertir dos monedas iguales", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        respuesta = objConversores.convertir(opcion, deMonedas, aMonedas, cantidad);
                        unidad = ((Spinner) findViewById(R.id.spnAMonedas)).getSelectedItem().toString();
                        break;

                    case 1: // Longitud
                        int deLongitud = ((Spinner) findViewById(R.id.spnDeLongitud)).getSelectedItemPosition();
                        int aLongitud = ((Spinner) findViewById(R.id.spnALongitud)).getSelectedItemPosition();

                        // Validación para evitar conversiones de la misma unidad
                        if (deLongitud == aLongitud) {
                            Toast.makeText(MainActivity.this, "No se pueden convertir dos unidades de longitud iguales", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        respuesta = objConversores.convertir(opcion, deLongitud, aLongitud, cantidad);
                        unidad = ((Spinner) findViewById(R.id.spnALongitud)).getSelectedItem().toString();
                        break;

                    // Repite para Tiempo, Almacenamiento y Transferencia de Datos
                }

                // Mostrar la respuesta
                tempVal = findViewById(R.id.lblRespuesta);
                tempVal.setText(String.format("Respuesta: %.2f %s", respuesta, unidad));
            }
        });
    }
}

class conversores {
    double[][] valores = {
            // Monedas
            {1, 0.98, 7.73, 25.45, 36.78, 508.87, 8.74, 0.0087, 0.0073, 0.0054, 0.049}, // Monedas
            // Longitud (metros, kilómetros, millas, pies, centímetros, milímetros, yardas, pulgadas, hectáreas, nanómetros)
            {1, 0.001, 0.000621371, 3.28084, 100, 1000, 1.09361, 39.3701, 0.0001, 1e9}, // Longitud
            {}, // Tiempo
            {}, // Almacenamiento
            {}, // Transferencia de datos
    };

    public double convertir(int opcion, int de, int a, double cantidad) {
        return (valores[opcion][a] / valores[opcion][de]) * cantidad;
    }
}