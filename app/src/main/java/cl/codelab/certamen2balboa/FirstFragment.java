/*
  Autor: Patricio Balboa
  Rut: 17.592.019-6
 */
package cl.codelab.certamen2balboa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import cl.codelab.certamen2balboa.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {
    Button btnAddCientifico;
    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



       binding.btnAddCientifico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText rut = view.findViewById(R.id.edtACid);
                EditText nombre= view.findViewById(R.id.edtACname);
                EditText apellido= view.findViewById(R.id.edtAClastname);
                RadioButton male = view.findViewById(R.id.rbCMale);
                RadioButton female = view.findViewById(R.id.rbCfemale);


                BDBalboa maneja = new BDBalboa(getContext());
                //maneja.addCientifico()
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}