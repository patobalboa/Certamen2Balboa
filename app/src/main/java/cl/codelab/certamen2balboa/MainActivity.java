package cl.codelab.certamen2balboa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class MainActivity extends AppCompatActivity {
    Fragment CientificoF, PlantasF, RecoleccionF;
    FragmentTransaction transaccion;
    Button btnCientifico, btnPlantas, btnRecoleccion, btnAutor;
    BDBalboa mantt;
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

        getSupportFragmentManager().beginTransaction().commit();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_cientifico, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.apiC) {
            getCientificoAPI getCientificoapi = new getCientificoAPI();
            getCientificoapi.execute();
            transaccion = getSupportFragmentManager().beginTransaction();
            transaccion.replace(R.id.fragmentContainerView, CientificoF);
            transaccion.addToBackStack(null);
            transaccion.commit();

            return true;
        } else if (item.getItemId() == R.id.apiP) {
            getPlantasAPI getPlantasapi = new getPlantasAPI();
            getPlantasapi.execute();
            return true;
        } else if (item.getItemId() == R.id.mapaID) {
            startActivity(new Intent(MainActivity.this, MapsActivity2.class));
        }else if(item.getItemId()== R.id.apiR){
            getRecoleccionApi getRecoleccionApi = new getRecoleccionApi();
            getRecoleccionApi.execute();
        }
            else {
            return false;
        }
        return false;
    }
    private class getCientificoAPI extends AsyncTask<Void, Void, String> {
        String json_GetString;

        @Override
        protected String doInBackground(Void... voids) {
            URL url = null;
            BDBalboa mantt = new BDBalboa(getBaseContext());

            try {
                url = new URL("http://www.codeplus.cl:8000/cientifico");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                assert url != null;
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                while ((json_GetString = bufferedReader.readLine()) != null){
                    stringBuffer.append(json_GetString).append("\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                JSONArray jsonArray = new JSONArray(stringBuffer.toString().trim());
                JSONObject jsonObject;

                for(int i = 0; i< jsonArray.length();i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    String rut = jsonObject.getString("rut");
                    String nom = jsonObject.getString("nombre_c");
                    String ape = jsonObject.getString("apellido_c");
                    String sex = jsonObject.getString("sexo");
                    mantt.addCientifico(Integer.parseInt(rut), nom, ape, sex);
                }

                return stringBuffer.toString().trim();

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
    }
    private class getPlantasAPI extends AsyncTask<Void, Void, String> {
        String json_GetString;
        BDBalboa mantt = new BDBalboa(getBaseContext());

        @Override
        protected String doInBackground(Void... voids) {
            URL url = null;

            try {
                url = new URL("http://www.codeplus.cl:8000/plantas");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                assert url != null;
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();


                while ((json_GetString = bufferedReader.readLine()) != null){
                    stringBuffer.append(json_GetString).append("\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                JSONArray jsonArray = new JSONArray(stringBuffer.toString().trim());
                JSONObject jsonObject;

                for(int i = 0; i< jsonArray.length();i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    String id = jsonObject.getString("id_planta");
                    String nom = jsonObject.getString("nombre_p");
                    String nomc = jsonObject.getString("nombre_cientifico_p");
                    String foto = jsonObject.getString("foto");
                    String uso = jsonObject.getString("uso_p");
                    Base64.getDecoder().decode(new String(foto).getBytes(StandardCharsets.UTF_8));



                    mantt.addPlantas(Integer.parseInt(id), nom, nomc, Base64.getDecoder().decode(new String(foto).getBytes(StandardCharsets.UTF_8)), uso);
                }
                return stringBuffer.toString().trim();

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
    }
    private class getRecoleccionApi extends AsyncTask<Void, Void, String> {
        String json_GetString;
        BDBalboa mantt = new BDBalboa(getBaseContext());

        @Override
        protected String doInBackground(Void... voids) {
            URL url = null;

            try {
                url = new URL("http://www.codeplus.cl:8000/recoleccion");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                assert url != null;
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();


                while ((json_GetString = bufferedReader.readLine()) != null){
                    stringBuffer.append(json_GetString).append("\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                JSONArray jsonArray = new JSONArray(stringBuffer.toString().trim());
                JSONObject jsonObject;

                for(int i = 0; i< jsonArray.length();i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    String id = jsonObject.getString("id");
                    String fecha = jsonObject.getString("fecha");
                    String idp = jsonObject.getString("id_planta");
                    String rut = jsonObject.getString("rut_c");
                    String comm = jsonObject.getString("comment");
                    String foto = jsonObject.getString("foto_lugar");
                    String loca = jsonObject.getString("localizacion");
                    Base64.getDecoder().decode(new String(foto).getBytes(StandardCharsets.UTF_8));



                    mantt.addRecoleccion(Integer.parseInt(id), fecha, Integer.parseInt(idp), Integer.parseInt(rut), Base64.getDecoder().decode(new String(foto).getBytes(StandardCharsets.UTF_8)),comm,loca);
                }
                return stringBuffer.toString().trim();

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
    }

}
