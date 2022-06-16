package cl.codelab.certamen2balboa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Fragment CientificoF, PlantasF, RecoleccionF;
    FragmentTransaction transaccion;
    Button btnCientifico, btnPlantas, btnRecoleccion, btnAutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRecoleccion = findViewById(R.id.btnRecoleccion);
        btnCientifico = findViewById(R.id.btnCientifico);
        btnPlantas = findViewById(R.id.btnPlantas);
        btnAutor = findViewById(R.id.btnAutor);
        CientificoF = new CientificoF();
        PlantasF = new PlantasF();
        RecoleccionF = new RecoleccionF();
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerView, CientificoF).commit();
        btnCientifico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaccion = getSupportFragmentManager().beginTransaction();
                transaccion.replace(R.id.fragmentContainerView, CientificoF);
                transaccion.addToBackStack(null);
                transaccion.commit();
            }
        });
        btnPlantas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaccion = getSupportFragmentManager().beginTransaction();
                transaccion.replace(R.id.fragmentContainerView, PlantasF);
                transaccion.addToBackStack(null);
                transaccion.commit();
            }
        });
        btnRecoleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaccion = getSupportFragmentManager().beginTransaction();
                transaccion.replace(R.id.fragmentContainerView, RecoleccionF);
                transaccion.addToBackStack(null);
                transaccion.commit();
            }
        });
        btnAutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saltarAutor(view);
            }
        });
    }
    public void saltarAutor(View view){
        Intent intent = new Intent(this, Autor.class);
        startActivity(intent);
    }
}