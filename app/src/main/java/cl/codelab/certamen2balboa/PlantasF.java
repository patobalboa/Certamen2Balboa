package cl.codelab.certamen2balboa;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;



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
    Intent camara;
    BDBalboa manplanta;
    Button buscarp, agregarp, eliminarp, modificarp;
    EditText idp, nombrep, nomcientp, usop;
    ListView lvPlantas;
    ImageView imgPlanta1, imgPlanta2;
    final static int cons= 0;
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
        manplanta = new BDBalboa(getContext());





        return viewp;
    }
    public boolean onLongClick(View view){
        int id;
        id=view.getId();
        switch (id){
            case R.id.imgFotoP:
                Toast.makeText(getContext(), "Intentando abrir camara", Toast.LENGTH_LONG).show();
                camara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camara, cons);
                Toast.makeText(getContext(), "Se abre la camara", Toast.LENGTH_LONG).show();
                break;
        }
        return false;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent datacam){
        super.onActivityResult(requestCode, resultCode, datacam);
        if(resultCode == Activity.RESULT_OK){
            Bundle ext = datacam.getExtras();
            bmp1 = (Bitmap) ext.get("datacam");
            imgPlanta1.setImageBitmap(bmp1);
            Toast.makeText(getContext(), "Tomamos la foto", Toast.LENGTH_LONG).show();
        }
    }
}