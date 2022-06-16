package cl.codelab.certamen2balboa;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlantasF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlantasF extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlantasF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlantasF.
     */
    // TODO: Rename and change types and number of parameters
    public static PlantasF newInstance(String param1, String param2) {
        PlantasF fragment = new PlantasF();
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

    View viewp;
    Activity act_camara;
    Intent camara;
    BDBalboa manplanta;
    Button buscarp, agregarp, eliminarp, modificarp, tomafoto;
    EditText idp, nombrep, nomcientp, usop;
    ListView lvPlantas;
    ImageView imgPlanta1, imgPlanta2;
    final static int cons = 0;
    Bitmap bmp1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewp = inflater.inflate(R.layout.fragment_plantas, container, false);
        buscarp = viewp.findViewById(R.id.btnBuscarP);
        agregarp = viewp.findViewById(R.id.btnAddP);
        eliminarp = viewp.findViewById(R.id.btnEliminarP);
        modificarp = viewp.findViewById(R.id.btnModificarP);
        idp = viewp.findViewById(R.id.edtIdP);
        nombrep = viewp.findViewById(R.id.edtNombreP);
        nomcientp = viewp.findViewById(R.id.edtNomCienP);
        usop = viewp.findViewById(R.id.edtUsoP);
        lvPlantas = viewp.findViewById(R.id.lvPlantas);
        imgPlanta1 = viewp.findViewById(R.id.imgFotoP);
        tomafoto = viewp.findViewById(R.id.btnTomarFotoP);
        manplanta = new BDBalboa(getContext());
        act_camara = getActivity();
        agregarp.setEnabled(true);
        eliminarp.setEnabled(false);
        modificarp.setEnabled(false);

        listaPlantas();
        agregarp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idp.getText().toString().isEmpty() || nombrep.toString().isEmpty() || nomcientp.toString().isEmpty() || usop.toString().isEmpty()) {
                    Toast.makeText(getContext(), "Debe rellenar todos los campos", Toast.LENGTH_LONG).show();
                } else {

                    int id = Integer.parseInt(idp.getText().toString());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp1.compress(Bitmap.CompressFormat.PNG,100,stream);
                    byte[] byteArray= stream.toByteArray();
                    if (manplanta.addPlantas(id, nombrep.getText().toString(), nomcientp.getText().toString(),byteArray, usop.getText().toString())) {
                        Toast.makeText(getContext(), "La planta se añadió con exito", Toast.LENGTH_LONG).show();
                        listaPlantas();
                    } else {
                        Toast.makeText(getContext(), "No se pudo agregar", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        eliminarp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(idp.getText().toString());
                if (manplanta.deletePlantas(id)) {
                    Toast.makeText(getContext(), "La planta fue Borrada", Toast.LENGTH_LONG).show();
                    listaPlantas();
                } else {

                    Toast.makeText(getContext(), "El id no existe", Toast.LENGTH_LONG).show();
                }
            }
        });
        buscarp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idp.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Debe colocar un ID", Toast.LENGTH_LONG).show();
                } else {
                    int id = Integer.parseInt(idp.getText().toString());

                    Plantas dato = manplanta.getPlantas(id);

                    String ruts = String.valueOf(id);
                    if (dato == null) {

                        agregarp.setEnabled(true);
                        eliminarp.setEnabled(false);
                        modificarp.setEnabled(false);
                        Toast.makeText(getContext(), "El ID " + ruts + " no existe", Toast.LENGTH_LONG).show();
                    } else {
                        agregarp.setEnabled(false);
                        eliminarp.setEnabled(true);
                        modificarp.setEnabled(true);
                        nombrep.setText(dato.getNombre_p());
                        nomcientp.setText(dato.getNombre_cientifico_planta());
                        usop.setText(dato.getUso());
                    }
                }

            }
        });
        modificarp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idp.getText().toString().isEmpty() || nombrep.toString().isEmpty() || nomcientp.toString().isEmpty() || usop.toString().isEmpty()) {
                    Toast.makeText(getContext(), "Debe rellenar todos los campos", Toast.LENGTH_LONG).show();
                } else {

                    int id = Integer.parseInt(idp.getText().toString());
                    if (manplanta.updatePlantas(id, nombrep.getText().toString(), nomcientp.getText().toString(), usop.getText().toString())) {
                        Toast.makeText(getContext(), "La planta se modificó con exito", Toast.LENGTH_LONG).show();
                        listaPlantas();
                    } else {
                        Toast.makeText(getContext(), "No se pudo modificar", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        tomafoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCamara();
            }
        });
        return viewp;
    }

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
        //startActivityForResult(intent, 1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bundle ext = data.getExtras();
            bmp1 = (Bitmap) ext.get("data");
            imgPlanta1.setImageBitmap(bmp1);
            Toast.makeText(getContext(), "capturamos foto", Toast.LENGTH_LONG).show();
        }
    }

    public void listaPlantas() {

        ArrayList<Plantas> lista = manplanta.getListPlantas();
        if (!lista.isEmpty()) {
            ArrayAdapter<Plantas> adapter = new ArrayAdapter<Plantas>(getContext(), android.R.layout.simple_list_item_1, lista);
            lvPlantas.setAdapter(adapter);
            lvPlantas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    agregarp.setEnabled(false);
                    eliminarp.setEnabled(true);
                    modificarp.setEnabled(true);
                    Plantas p = lista.get(i);
                    int rutitem = p.getId();
                    String rutET = String.valueOf(rutitem);
                    idp.setText(rutET);
                    nombrep.setText(p.getNombre_p());
                    nomcientp.setText(p.getNombre_cientifico_planta());
                    usop.setText(p.getUso());
                    if(p.getFoto()!= null) {
                        bmp1 = BitmapFactory.decodeByteArray(p.getFoto(), 0, p.getFoto().length);
                        imgPlanta1.setImageBitmap(bmp1);
                    }


                }

            });
        }

    }


}
