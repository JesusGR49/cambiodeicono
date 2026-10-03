package com.example.cambiodeicono;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LinearLayout rootLayout;
    private TextView tvHex;
    private Button btnFav, btnTheme;
    private GridLayout gridPalette;
    private ListView listFav;

    private String colorSeleccionadoHex = "#000000"; // Color por defecto
    private ArrayList<String> favoritosList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rootLayout = findViewById(R.id.root);
        tvHex = findViewById(R.id.tvHex);
        btnFav = findViewById(R.id.btnFav);
        btnTheme = findViewById(R.id.btnTheme);
        gridPalette = findViewById(R.id.gridPalette);
        listFav = findViewById(R.id.listFav);


        favoritosList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoritosList);
        listFav.setAdapter(adapter);


        cargarColores();


        btnTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rootLayout.setBackgroundColor(Color.parseColor(colorSeleccionadoHex));
                Toast.makeText(MainActivity.this, "Tema aplicado", Toast.LENGTH_SHORT).show();
            }
        });


        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!favoritosList.contains(colorSeleccionadoHex)) {
                    favoritosList.add(colorSeleccionadoHex);
                    adapter.notifyDataSetChanged(); // Actualiza la vista de la lista
                    Toast.makeText(MainActivity.this, "Guardado en favoritos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Este color ya está en favoritos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void cargarColores() {
        for (int i = 1; i <= 24; i++) {
            // Buscamos el color c1, c2, c3... en el archivo colors.xml
            int resID = getResources().getIdentifier("c" + i, "color", getPackageName());

            if (resID != 0) { // Si existe el color
                int colorValue = ContextCompat.getColor(this, resID);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue)); // Sacamos su código Hexadecimal


                Button colorBtn = new Button(this);
                colorBtn.setBackgroundColor(colorValue); // Le pintamos el fondo


                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = 150;
                params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Para que ocupe espacio equitativo
                params.setMargins(8, 8, 8, 8); // Separación entre botones
                colorBtn.setLayoutParams(params);


                colorBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        colorSeleccionadoHex = hexColor;
                        tvHex.setText(hexColor);
                    }
                });


                gridPalette.addView(colorBtn);
            }
        }
    }
}