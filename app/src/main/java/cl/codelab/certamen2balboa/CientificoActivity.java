package cl.codelab.certamen2balboa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class CientificoActivity extends AppCompatActivity {
    Fragment anadirCientifico, listarCientifico;
    FragmentTransaction transaccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cientifico);
        anadirCientifico = new anadirCientifico();
        listarCientifico = new listarCientifico();
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerView, listarCientifico).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cientifico,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.item_addCientifico) {
            transaccion = getSupportFragmentManager().beginTransaction();
            transaccion.replace(R.id.fragmentContainerView, anadirCientifico);
            transaccion.addToBackStack(null);
            transaccion.commit();
        }
        return true;
    }

}