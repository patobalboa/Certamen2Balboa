package cl.codelab.certamen2balboa;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import android.widget.Toast;

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

    BDBalboa maneja;
    EditText idR, fechaR, comementR, localR;
    Spinner spPlanta, spCientifico;
    ImageView fotolugarR;
    Button buscarR, btnFotoR, addR, eliminarR, modificarR;
    String datoSpC;
    ArrayList<String> listadoCientifico;
    ArrayList<Cientifico> CientificoList;
    ListView lvReco;




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
        btnFotoR = view.findViewById(R.id.btnTomarFotoP);
        addR = view.findViewById(R.id.btnAddR);
        eliminarR = view.findViewById(R.id.btnEliminarR);
        modificarR = view.findViewById(R.id.btnModificarR);
        fotolugarR = view.findViewById(R.id.imgFotoP);
        lvReco = view.findViewById(R.id.lvRecoleccion);

        spCientifico = view.findViewById(R.id.spCientifico);
        maneja = new BDBalboa(getContext());

        listaReco();


        consultaListaCientifico();
        ArrayAdapter<Cientifico> adapter1 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,listadoCientifico);
        //spCientifico.setAdapter(adapter1);

        buscarR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idR.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Por favor rellene el ID", Toast.LENGTH_LONG).show();
                }else
                {
                    int idre = Integer.parseInt(idR.getText().toString());
                    Recoleccion dato =  maneja.getRecoleccion(idre);
                    if(dato == null){
                        addR.setEnabled(true);
                        eliminarR.setEnabled(false);
                        modificarR.setEnabled(false);
                        Toast.makeText(getContext(), "El ID no exite", Toast.LENGTH_LONG).show();

                    }else{
                        addR.setEnabled(false);
                        eliminarR.setEnabled(true);
                        modificarR.setEnabled(true);
                        fechaR.setText(dato.getFecha());
                        comementR.setText(dato.getComentario());
                        localR.setText(dato.getLocalizacion());
                        Toast.makeText(getContext(), "Guardado con exito", Toast.LENGTH_LONG).show();

                    }
                }
            }
        });
        addR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idR.toString().isEmpty() || fechaR.toString().isEmpty() || comementR.toString().isEmpty() || localR.toString().isEmpty()){
                    Toast.makeText(getContext(), "Por favor rellene los datos", Toast.LENGTH_LONG).show();
                }else{
                    int idre = Integer.parseInt(idR.getText().toString());
                    maneja.addRecoleccion(idre,fechaR.getText().toString(),comementR.getText().toString(),localR.getText().toString());
                    Toast.makeText(getContext(), "Se ingresaron los datos", Toast.LENGTH_LONG).show();
                    listaReco();
                }
            }
        });

        eliminarR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(maneja.deleteRecoleccion(Integer.parseInt(idR.getText().toString()))){
                    Toast.makeText(getContext(), "La recoleccion fue Borrada", Toast.LENGTH_LONG).show();
                    listaReco();
                }
                else {

                    Toast.makeText(getContext(), "El ID no existe", Toast.LENGTH_LONG).show();
                }
            }
        });
        modificarR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idR.toString().isEmpty() || fechaR.toString().isEmpty() || comementR.toString().isEmpty() || localR.toString().isEmpty()){
                    Toast.makeText(getContext(), "Por favor rellene los datos", Toast.LENGTH_LONG).show();
                }else{
                    int idre = Integer.parseInt(idR.getText().toString());
                    maneja.updateRecoleccion(idre,fechaR.getText().toString(),comementR.getText().toString(),localR.getText().toString());
                    Toast.makeText(getContext(), "Se modificaron los datos", Toast.LENGTH_LONG).show();
                    listaReco();
                }
            }
        });








        return view;
    }
    private void consultaListaCientifico(){
        SQLiteDatabase conn = maneja.getReadableDatabase();
        Cientifico cientifico = null;
        CientificoList = new ArrayList<Cientifico>();
        Cursor cursor = conn.rawQuery("SELECT * FROM CientificoBalboa", null);
        while (cursor.moveToNext()){

            cientifico = new Cientifico();
            cientifico.setNombre(cursor.getString(1));
            cientifico.setRut(cursor.getInt(0));
            cientifico.setApellido(cursor.getString(2));

            Log.i("Rut: ", String.valueOf(cientifico.getRut()));
            Log.i("Nombre:", cientifico.getNombre());
            Log.i("Apellido: ",cientifico.getApellido());


            CientificoList.add(cientifico);
        }
        obtenerLista();


    }
    private void obtenerLista(){
        listadoCientifico = new ArrayList<String>();
        listadoCientifico.add("Seleccione Cientifico");
        for(int i=0;i<CientificoList.size();i++){
            listadoCientifico.add(CientificoList.get(i).getNombre()+" "+CientificoList.get(i).getApellido());
        }
    }

    private void listaReco(){

        ArrayList<Recoleccion> lista = maneja.getListRecoleccion();
        if(!lista.isEmpty()){
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

                }

            });
        }

    }

}