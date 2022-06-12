package cl.codelab.certamen2balboa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button cientifico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cientifico = findViewById(R.id.btnIrCientifico);

    }
    public void saltoCientifico(View view){
        Intent intent = new Intent(this, CientificoActivity.class);
        startActivity(intent);
    }
}