package cl.codelab.certamen2balboa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link listarCientifico#newInstance} factory method to
 * create an instance of this fragment.
 */
public class listarCientifico extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public listarCientifico() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment listarCientifico.
     */
    // TODO: Rename and change types and number of parameters
    public static listarCientifico newInstance(String param1, String param2) {
        listarCientifico fragment = new listarCientifico();
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
    ListView lvCientifico;
    Button refrescar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_listar_cientifico, container, false);
        lvCientifico = view.findViewById(R.id.lvCientifico);
        refrescar = view.findViewById(R.id.refrescar);
        refrescar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaCientifico();
                Toast.makeText(getContext(), "Refrescado el ListView", Toast.LENGTH_LONG).show();
            }
        });
        listaCientifico();
        return view;
    }
    public void listaCientifico(){
        BDBalboa maneja = new BDBalboa(getContext());
        ArrayList<Cientifico> lista = maneja.getListCientifico();
        if(!lista.isEmpty()){
            ArrayAdapter<Cientifico> adapter = new ArrayAdapter<Cientifico>(getContext(), android.R.layout.simple_list_item_1, lista);
            lvCientifico.setAdapter(adapter);
        }
    }
}