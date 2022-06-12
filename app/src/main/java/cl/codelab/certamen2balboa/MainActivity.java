package cl.codelab.certamen2balboa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnCientifico, btnPlantas, btnRecoleccion, btnAutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAutor = findViewById(R.id.btnAutor);

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