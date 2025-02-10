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

                int opcion = tbh.getCurrentTab();

                spn = findViewById(R.id.spnDeMonedas);
                int de = spn.getSelectedItemPosition();

                spn = findViewById(R.id.spnAMonedas);
                int a = spn.getSelectedItemPosition();

                // Validación para verificar si las unidades son iguales
                if (de == a) {
                    Toast.makeText(MainActivity.this, "No se pueden convertir dos monedas iguales", Toast.LENGTH_SHORT).show();
                    return; // Detener la ejecución si las unidades son iguales
                }

                double cantidad = Double.parseDouble(cantidadTexto);

                tempVal = findViewById(R.id.lblRespuesta);
                double respuesta = objConversores.convertir(opcion, de, a, cantidad);

                // Formatear la respuesta con dos decimales y el símbolo de la moneda
                String[] simbolosMonedas = {"$", "€", "Q", "L", "C$", "₡", "₡", "¥", "£", "₹", "₱"};
                String simbolo = simbolosMonedas[a];
                tempVal.setText(String.format("Respuesta: %s %.2f", simbolo, respuesta));
            }
        });
    }
}

class conversores {
    double[][] valores = {
            // Dólar, Euro, Quetzal, Lempira, Córdoba, Colón CR, Colón SV, Yen, Libra, Rupia, Peso MX
            {1, 0.98, 7.73, 25.45, 36.78, 508.87, 8.74, 0.0087, 0.0073, 0.0054, 0.049}, // Monedas
            {}, // Longitud
            {}, // Tiempo
            {}, // Almacenamiento
            {}, // Transferencia de datos
    };

    public double convertir(int opcion, int de, int a, double cantidad) {
        return (valores[opcion][a] / valores[opcion][de]) * cantidad;
    }
}