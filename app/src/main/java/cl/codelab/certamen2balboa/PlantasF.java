package cl.codelab.certamen2balboa;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

import com.google.common.util.concurrent.ListenableFuture;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlantasF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlantasF extends Fragment{

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
        tomafoto = viewp.findViewById(R.id.btnTomarFotoP);
        manplanta = new BDBalboa(getContext());
        act_camara = getActivity();


        tomafoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, 1);
                }
                //if(intent.resolveActivity(act_camara.getPackageManager())!= null){
                    getActivity().startActivityForResult(intent,1);
                //    startActivityForResult(intent, 1);
                //}
            }
        });







        return viewp;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK)
        {
            Bundle ext=data.getExtras();
            Bitmap bmp1=(Bitmap)ext.get("data");
            imgPlanta1.setImageBitmap(bmp1);
            Toast.makeText(getContext(), "capturamos foto", Toast.LENGTH_LONG).show();

        }
    }







}