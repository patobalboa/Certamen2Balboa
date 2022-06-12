package cl.codelab.certamen2balboa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link anadirCientifico#newInstance} factory method to
 * create an instance of this fragment.
 */
public class anadirCientifico extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public anadirCientifico() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment anadirCientifico.
     */
    // TODO: Rename and change types and number of parameters
    public static anadirCientifico newInstance(String param1, String param2) {
        anadirCientifico fragment = new anadirCientifico();
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
    EditText rut;
    EditText nombre;
    EditText apellido;
    RadioButton male, female;
    Button agregar;
    boolean valida;
    String sexo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_anadir_cientifico, container, false);
        view =inflater.inflate(R.layout.fragment_anadir_cientifico, container, false);

        rut =(EditText) view.findViewById(R.id.edtACid);
        nombre= view.findViewById(R.id.edtACname);
        apellido= view.findViewById(R.id.edtAClastname);
        male = view.findViewById(R.id.rbCMale);
        female = view.findViewById(R.id.rbCfemale);
        agregar = view.findViewById(R.id.btnAddCientifico);
        sexo="";

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valida =false;
                if (male.isChecked()) {
                    valida = true;
                    sexo = "masculino";
                }else if(female.isChecked()){
                    valida=true;
                    sexo = "femenino";
                }else{
                    Toast.makeText( getContext(),"Debes seleccionar un sexo",Toast.LENGTH_LONG).show();
                }
                if(valida) {
                    int ruti = Integer.parseInt(rut.getText().toString());
                    BDBalboa maneja = new BDBalboa(getContext());
                    if (maneja.addCientifico(ruti, nombre.getText().toString(), apellido.getText().toString(), sexo)) {
                        Toast.makeText(getContext(), "Se guardó con exito", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Vuelva a intentarlo", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        return view;
    }

}