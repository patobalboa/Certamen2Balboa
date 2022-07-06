package cl.codelab.certamen2balboa;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecoleccionF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecoleccionF extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecoleccionF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecoleccionF.
     */
    // TODO: Rename and change types and number of parameters
    public static RecoleccionF newInstance(String param1, String param2) {
        RecoleccionF fragment = new RecoleccionF();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View view;
    Intent intent2;
    BDBalboa maneja;
    TextView estado;
    EditText idR, fechaR, comementR, localR;
    Spinner spPlanta, spCientifico;
    ImageView fotolugarR;
    Button buscarR, btnFotoR, addR, eliminarR, modificarR, bGPS;
    String datoSpC;
    ArrayList<String> listadoCientifico, listadoPlantas;
    ArrayList<Cientifico> CientificoList;
    ArrayList<Plantas> PlantasList;
    ListView lvReco;
    Bitmap bmp2;
    Location location;
    LocationManager locationManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recoleccion, container, false);
        idR = view.findViewById(R.id.edtIdR);
        fechaR = view.findViewById(R.id.edtFechaR);
        comementR = view.findViewById(R.id.edtCommentR);
        localR = view.findViewById(R.id.edtLocaR);
        buscarR = view.findViewById(R.id.btnBuscarR);
        btnFotoR = view.findViewById(R.id.btnFotoLugarR);
        addR = view.findViewById(R.id.btnAddR);
        eliminarR = view.findViewById(R.id.btnEliminarR);
        modificarR = view.findViewById(R.id.btnModificarR);
        fotolugarR = view.findViewById(R.id.imgFotoLugarR);
        lvReco = view.findViewById(R.id.lvRecoleccion);
        bGPS = view.findViewById(R.id.btnGPS);
        spCientifico = view.findViewById(R.id.spCientificoR);
        spPlanta = view.findViewById(R.id.spPlantaR);
        maneja = new BDBalboa(getContext());
        estado = view.findViewById(R.id.txvEstado);

        listaReco();


        consultaListaCientifico();
        consultaListaPlantas();

        ArrayAdapter<String> adapter1 = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listadoCientifico);
        ArrayAdapter<String> adapter2 = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listadoPlantas);
        spCientifico.setAdapter(adapter1);
        spPlanta.setAdapter(adapter2);

        bGPS.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.S)
            @Override
            public void onClick(View view) {
                getGPS();
            }
        });
        buscarR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idR.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Por favor rellene el ID", Toast.LENGTH_LONG).show();
                } else {
                    int idre = Integer.parseInt(idR.getText().toString());
                    Recoleccion dato = maneja.getRecoleccion(idre);
                    if (dato == null) {
                        addR.setEnabled(true);
                        eliminarR.setEnabled(false);
                        modificarR.setEnabled(false);
                        Toast.makeText(getContext(), "El ID no exite", Toast.LENGTH_LONG).show();

                    } else {
                        addR.setEnabled(false);
                        eliminarR.setEnabled(true);
                        modificarR.setEnabled(true);
                        fechaR.setText(dato.getFecha());
                        comementR.setText(dato.getComentario());
                        localR.setText(dato.getLocalizacion());
                        Toast.makeText(getContext(), "Guardado con exito", Toast.LENGTH_LONG).show();
                        if (dato.getFoto_lugar() != null) {
                            bmp2 = BitmapFactory.decodeByteArray(dato.getFoto_lugar(), 0, dato.getFoto_lugar().length);
                            fotolugarR.setImageBitmap(bmp2);
                        }

                    }
                }
            }
        });
        addR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idR.toString().isEmpty() || fechaR.toString().isEmpty() || comementR.toString().isEmpty() || localR.toString().isEmpty() || spPlanta.getSelectedItem().toString().equals("Seleccione Planta") || spCientifico.getSelectedItem().toString().equals("Seleccione Cientifico")) {
                    Toast.makeText(getContext(), "Por favor rellene los datos", Toast.LENGTH_LONG).show();
                } else {
                    int planta_id = getIdPlanta(spPlanta.getSelectedItemPosition());
                    int cientifico_id = getIdCientifico(spCientifico.getSelectedItemPosition());

                    int idre = Integer.parseInt(idR.getText().toString());
                    if (bmp2 != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp2.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        if (maneja.addRecoleccion(idre, fechaR.getText().toString(), planta_id, cientifico_id, byteArray, comementR.getText().toString(), localR.getText().toString())) {
                            Toast.makeText(getContext(), "Se ingresaron los datos", Toast.LENGTH_LONG).show();
                            postRecAPI p = new postRecAPI();
                            p.execute();
                            listaReco();
                        }
                    } else {
                        new AlertDialog.Builder(getContext())
                                .setTitle("ALERTA")
                                .setMessage("No ha agregado una foto")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                }).show();
                    }
                }
            }
        });

        eliminarR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (maneja.deleteRecoleccion(Integer.parseInt(idR.getText().toString()))) {
                    Toast.makeText(getContext(), "La recoleccion fue Borrada", Toast.LENGTH_LONG).show();
                    listaReco();
                } else {

                    Toast.makeText(getContext(), "El ID no existe", Toast.LENGTH_LONG).show();
                }
            }
        });
        modificarR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idR.toString().isEmpty() || fechaR.toString().isEmpty() || comementR.toString().isEmpty() || localR.toString().isEmpty() || spPlanta.getSelectedItem().toString().equals("Seleccione Planta") || spCientifico.getSelectedItem().toString().equals("Seleccione Cientifico")) {
                    Toast.makeText(getContext(), "Por favor rellene los datos", Toast.LENGTH_LONG).show();
                } else {
                    int idre = Integer.parseInt(idR.getText().toString());
                    int planta_id = getIdPlanta(spPlanta.getSelectedItemPosition());
                    int cientifico_id = getIdCientifico(spCientifico.getSelectedItemPosition());

                    if (bmp2 != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp2.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        if (maneja.updateRecoleccion(idre, fechaR.getText().toString(), planta_id, cientifico_id, byteArray, comementR.getText().toString(), localR.getText().toString())) {

                            Toast.makeText(getContext(), "Se modificaron los datos", Toast.LENGTH_LONG).show();
                            listaReco();
                        } else {
                            Toast.makeText(getContext(), "No se modifico nada", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        new AlertDialog.Builder(getContext())
                                .setTitle("ALERTA")
                                .setMessage("No ha agregado una foto")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                }).show();
                    }
                }
            }
        });
        btnFotoR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCamara();
            }
        });

        return view;
    }

    private void abrirCamara() {
        intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent2, 1);
        //startActivityForResult(intent, 1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bundle ext = data.getExtras();
            bmp2 = (Bitmap) ext.get("data");
            Log.i("BITMAP: ", String.valueOf(bmp2));
            if (bmp2 != null) {
                fotolugarR.setImageBitmap(bmp2);
                Toast.makeText(getContext(), "capturamos foto", Toast.LENGTH_LONG).show();
            } else {

                Toast.makeText(getContext(), "No se pudo guardar la foto", Toast.LENGTH_LONG).show();
            }

        }
    }

    private int getIdPlanta(int pos) {
        SQLiteDatabase conn = maneja.getReadableDatabase();

        Cursor cursor = conn.rawQuery("SELECT * FROM PlantasBalboa", null);
        cursor.moveToPosition(pos - 1);


        return cursor.getInt(0);
    }

    private int getIdCientifico(int pos) {
        SQLiteDatabase conn = maneja.getReadableDatabase();

        Cursor cursor = conn.rawQuery("SELECT * FROM CientificoBalboa", null);
        cursor.moveToPosition(pos - 1);


        return cursor.getInt(0);
    }

    private void consultaListaCientifico() {
        SQLiteDatabase conn = maneja.getReadableDatabase();
        Cientifico cientifico = null;
        CientificoList = new ArrayList<Cientifico>();
        Cursor cursor = conn.rawQuery("SELECT * FROM CientificoBalboa", null);
        while (cursor.moveToNext()) {

            cientifico = new Cientifico();
            cientifico.setNombre(cursor.getString(1));
            cientifico.setRut(cursor.getInt(0));
            cientifico.setApellido(cursor.getString(2));

            Log.i("Rut: ", String.valueOf(cientifico.getRut()));
            Log.i("Nombre:", cientifico.getNombre());
            Log.i("Apellido: ", cientifico.getApellido());


            CientificoList.add(cientifico);
        }
        obtenerLista();


    }

    private void obtenerLista() {
        listadoCientifico = new ArrayList<String>();
        listadoCientifico.add("Seleccione Cientifico");
        for (int i = 0; i < CientificoList.size(); i++) {
            listadoCientifico.add(CientificoList.get(i).getNombre() + " " + CientificoList.get(i).getApellido());

        }
    }

    private void consultaListaPlantas() {
        SQLiteDatabase conn = maneja.getReadableDatabase();
        Plantas cientifico = null;
        PlantasList = new ArrayList<Plantas>();
        Cursor cursor = conn.rawQuery("SELECT * FROM PlantasBalboa", null);
        while (cursor.moveToNext()) {

            cientifico = new Plantas();
            cientifico.setId(cursor.getInt(0));
            cientifico.setNombre_p(cursor.getString(1));

            PlantasList.add(cientifico);
        }
        obtenerListaP();


    }

    private void obtenerListaP() {
        listadoPlantas = new ArrayList<String>();
        listadoPlantas.add("Seleccione Planta");
        for (int i = 0; i < PlantasList.size(); i++) {
            listadoPlantas.add(PlantasList.get(i).getId() + " " + PlantasList.get(i).getNombre_p());

        }
    }

    private void listaReco() {

        ArrayList<Recoleccion> lista = maneja.getListRecoleccion();
        if (!lista.isEmpty()) {
            ArrayAdapter<Recoleccion> adapter = new ArrayAdapter<Recoleccion>(getContext(), android.R.layout.simple_list_item_1, lista);
            lvReco.setAdapter(adapter);
            lvReco.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    addR.setEnabled(false);
                    eliminarR.setEnabled(true);
                    modificarR.setEnabled(true);
                    Recoleccion p = lista.get(i);
                    int rutitem = p.getId();
                    String rutET = String.valueOf(rutitem);
                    idR.setText(rutET);
                    fechaR.setText(p.getFecha());
                    comementR.setText(p.getComentario());
                    localR.setText(p.getLocalizacion());
                    spPlanta.setSelection(getPosPlanta(p.getCod_planta() + 1));
                    spCientifico.setSelection(getPosCientifico(p.getCod_cientifico() + 1));
                    if (p.getFoto_lugar() != null) {
                        bmp2 = BitmapFactory.decodeByteArray(p.getFoto_lugar(), 0, p.getFoto_lugar().length);
                        fotolugarR.setImageBitmap(bmp2);
                    }

                }

            });
        }

    }

    private int getPosPlanta(int pos) {
        SQLiteDatabase conn = maneja.getReadableDatabase();
        Cursor cursor = conn.rawQuery("SELECT * FROM PlantasBalboa", null);
        int indice = 0;
        while (cursor.moveToNext()) {
            if (pos == cursor.getInt(0)) {
                return indice;
            } else {
                indice++;
            }
        }
        return indice;
    }

    private int getPosCientifico(int pos) {
        SQLiteDatabase conn = maneja.getReadableDatabase();
        Cursor cursor = conn.rawQuery("SELECT * FROM CientificoBalboa", null);
        int indice = 0;
        while (cursor.moveToNext()) {
            if (pos == cursor.getInt(0)) {
                return indice;
            } else {
                indice++;
            }
        }
        return indice;
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    public void getGPS() {
        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            alerta_GPS();
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]
                    {
                            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        };
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 10, 1, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                localR.setText(String.valueOf(location.getLongitude()) +","+String.valueOf(location.getLatitude()));
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                LocationListener.super.onProviderDisabled(provider);
                Toast.makeText(getContext(),"GPS DESACTIVADO \n ACTIVELO",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                LocationListener.super.onProviderEnabled(provider);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public void onResume() {
        super.onResume();
        getGPS();
    }
    public void alerta_GPS(){
        new AlertDialog.Builder(getContext())
                .setTitle("Activar GPS")
                .setMessage("¿El GPS esta desactivado  ¿Desea Activarlo??")

                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));/*Abre App para Activa el GPS*/
                                //dialog.cancel();
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();

    }
    private class postRecAPI extends AsyncTask<String,String,Boolean> {
        View view;
        EditText id;
        URL url;
        @Override
        protected Boolean doInBackground(String... params) {

            try {
                url = new URL("http://www.codeplus.cl:8000/recoleccion/add");

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            urlConnection.setRequestProperty("Content-Type", "application/json");

            urlConnection.setRequestMethod("POST");

            String id =idR.getText().toString();
            String fecha = fechaR.getText().toString();
            int idplanta = getIdPlanta(spPlanta.getSelectedItemPosition());
            int idcient = getIdCientifico(spCientifico.getSelectedItemPosition());
            String comm = comementR.getText().toString();
            String gps = localR.getText().toString();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",id);
                jsonObject.put("fecha", fecha);
                jsonObject.put("planta", idplanta);
                jsonObject.put("rut",idcient);
                jsonObject.put("comment", comm);

                jsonObject.put("localizacion", gps);

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes(jsonObject.toString());
                wr.flush();
                wr.close();
                if(urlConnection.getResponseCode()==200){
                    Log.i("Que pasó: ", "OK");
                    return true;
                }else{
                    Log.i("Que pasó: ", jsonObject.toString());
                    return false;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                estado.setText("Guardado Remoto con exito");
                estado.setTextColor(Color.GREEN);
            }else{
                estado.setText("Guardado Remoto Fallido");
                estado.setTextColor(Color.RED);
            }
        }
    }


}