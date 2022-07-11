/*
  Autor: Patricio Balboa
  Rut: 17.592.019-6
 */

package cl.codelab.certamen2balboa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CientificoF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CientificoF extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CientificoF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CientificoF.
     */
    // TODO: Rename and change types and number of parameters
    public static CientificoF newInstance(String param1, String param2) {
        CientificoF fragment = new CientificoF();
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
    private BDBalboa maneja;
    View view;
    String sexoC;
    EditText rut;
    EditText nombre;
    EditText apellido;
    ListView lvCientifico;
    Spinner spinner;

    Button agregar, eliminar, modificar, buscar;
    boolean valida;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cientifico, container, false);
        rut =(EditText) view.findViewById(R.id.edtRut);
        nombre= view.findViewById(R.id.edtNameC);
        apellido= view.findViewById(R.id.edtApellidoC);
        agregar = view.findViewById(R.id.btnAgregarC);
        eliminar = view.findViewById(R.id.btnBorrarC);
        modificar = view.findViewById(R.id.btnModificarC);
        buscar = view.findViewById(R.id.btnBuscarC);
        lvCientifico = view.findViewById(R.id.lvListaC);

        maneja = new BDBalboa(getContext());
        listaCientifico();
        agregar.setEnabled(true);
        eliminar.setEnabled(false);
        modificar.setEnabled(false);


        spinner = view.findViewById(R.id.spCientifico);
        String[] genero = new String[]{
                "Seleccione Sexo",
                "Masculino",
                "Femenino"
        };
         List<String> sexoList = new ArrayList<>(Arrays.asList(genero));

         ArrayAdapter adapterArr = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, sexoList);
         spinner.setAdapter(adapterArr);
         spinner.setSelection(0);


        //Botones CIENTIFICO
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rut.getText().toString().isEmpty() || nombre.toString().isEmpty()|| apellido.toString().isEmpty() || spinner.getSelectedItem().toString().equals("Seleccione Sexo")){
                    Toast.makeText(getContext(), "Debe rellenar todos los campos", Toast.LENGTH_LONG).show();
                }else{
                    sexoC = spinner.getSelectedItem().toString();
                    int ruti = Integer.parseInt(rut.getText().toString());
                    if (maneja.addCientifico(ruti, nombre.getText().toString(), apellido.getText().toString(), sexoC)) {
                        Toast.makeText(getContext(), "El cientifico se añadió con exito", Toast.LENGTH_LONG).show();
                        listaCientifico();
                    } else {
                        Toast.makeText(getContext(), "No se pudo agregar", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rutd = Integer.parseInt(rut.getText().toString());
                if (maneja.deleteCientifico(rutd)) {
                    Toast.makeText(getContext(), "El Cientifico fue Borrado", Toast.LENGTH_LONG).show();
                    listaCientifico();
                } else {

                    Toast.makeText(getContext(), "El Rut no existe", Toast.LENGTH_LONG).show();
                }
            }
        });
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rut.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Debe colocar un RUT", Toast.LENGTH_LONG).show();
                }else {
                    int ruti = Integer.parseInt(rut.getText().toString());

                    Cientifico dato = maneja.getCientifico(ruti);

                    String ruts = String.valueOf(ruti);
                    if (dato == null) {

                        agregar.setEnabled(true);
                        eliminar.setEnabled(false);
                        modificar.setEnabled(false);
                        Toast.makeText(getContext(), "El Rut "+ruts+" no existe", Toast.LENGTH_LONG).show();
                    } else {
                        agregar.setEnabled(false);
                        eliminar.setEnabled(true);
                        modificar.setEnabled(true);
                        nombre.setText(dato.getNombre());
                        apellido.setText(dato.getApellido());
                        if (dato.getSexo().equals("Masculino")) {
                            spinner.setSelection(1);
                        } else {
                            spinner.setSelection(2);
                        }
                    }
                }

            }
        });
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rut.getText().toString().isEmpty() || nombre.toString().isEmpty()|| apellido.toString().isEmpty() || spinner.getSelectedItem().toString().equals("Seleccione Sexo")){
                    Toast.makeText(getContext(), "Debe rellenar todos los campos", Toast.LENGTH_LONG).show();
                }else{
                    sexoC = spinner.getSelectedItem().toString();
                    int ruti = Integer.parseInt(rut.getText().toString());
                    if (maneja.updateCientifico(ruti, nombre.getText().toString(), apellido.getText().toString(), sexoC)) {
                        Toast.makeText(getContext(), "El cientifico se modificó con exito", Toast.LENGTH_LONG).show();
                        listaCientifico();
                    } else {
                        Toast.makeText(getContext(), "No se pudo modificar", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return view;
    }

    public void listaCientifico(){

        ArrayList<Cientifico> lista = maneja.getListCientifico();
        if(!lista.isEmpty()){
            ArrayAdapter<Cientifico> adapter2 = new ArrayAdapter<Cientifico>(getContext(), android.R.layout.simple_list_item_1, lista);
            lvCientifico.setAdapter(adapter2);
            lvCientifico.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    agregar.setEnabled(false);
                    eliminar.setEnabled(true);
                    modificar.setEnabled(true);
                    Cientifico p = lista.get(i);
                    int rutitem = p.getRut();
                    String rutET = String.valueOf(rutitem);
                    rut.setText(rutET);
                    apellido.setText(p.getApellido());
                    nombre.setText(p.getNombre());
                    if (p.getSexo().equals("Masculino")) {
                        spinner.setSelection(1);
                    } else {
                        Toast.makeText(getContext(),p.getSexo(), Toast.LENGTH_LONG).show();
                        spinner.setSelection(2);
                    }
                }

            });
        }

    }

}