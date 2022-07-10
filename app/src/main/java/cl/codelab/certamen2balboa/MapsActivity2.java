package cl.codelab.certamen2balboa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import cl.codelab.certamen2balboa.databinding.ActivityMaps2Binding;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private ActivityMaps2Binding binding;
    BDBalboa bd;
    ArrayList<Recoleccion> lista;
    public static final PolylineOptions POLILINEA =new PolylineOptions();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bd = new BDBalboa(getBaseContext());
        lista = bd.getListRecoleccion();


        binding = ActivityMaps2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-37.2843045, -72.7122427);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marcador en Laja"));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12.0f));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Nullable
            @Override
            public View getInfoWindow(@NonNull Marker marker) {
                return null;
            }

            @Nullable
            @Override
            public View getInfoContents(@NonNull Marker marker) {
                return null;
            }
        });
        lista.forEach((n) -> {

                String[] gps = n.getLocalizacion().split(",");
                Plantas plantas = new Plantas();
                Cientifico cientifico = new Cientifico();
                cientifico = bd.getCientifico(n.getCod_cientifico());
                plantas = bd.getPlantas(n.getCod_planta());
                byte[] foto = plantas.getFoto();
                String nom_cientifico = cientifico.getNombre()+" "+cientifico.getApellido();

                Bitmap bmpM = BitmapFactory.decodeByteArray(foto, 0, foto.length);
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(gps[1]), Double.parseDouble(gps[0])))
                        .title(plantas.getNombre_p())
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(bmpM, 120, 120)))
                        .snippet(
                        "Fecha: "+n.getFecha()+"" +
                                "\nRecolector: "+nom_cientifico+"" +
                                "\nNombre Cientifico: "+plantas.getNombre_cientifico_planta()+"" +
                                "\nComentario: "+n.getComentario()+"" +
                                "\nFoto Lugar: \n"+BitmapFactory.decodeByteArray(n.getFoto_lugar(),0,n.getFoto_lugar().length)
                ));
                POLILINEA.add(new LatLng(Double.parseDouble(gps[1]), Double.parseDouble(gps[0])));
        });
        if(!POLILINEA.toString().equals("")) {
            mMap.addPolyline(POLILINEA);
        }
    }
    public Bitmap resizeMapIcons(Bitmap iconName,int width, int height){
        //Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        return Bitmap.createScaledBitmap(iconName, width, height, false);
    }


    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        marker.showInfoWindow();
    }
}