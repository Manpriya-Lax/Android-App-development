package com.example.tic_tac_toe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class startFragment extends Fragment {



    private RadioButton playerModeRadioButton;
    private Button startButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        playerModeRadioButton = view.findViewById(R.id.player_mode);
        startButton = view.findViewById(R.id.start);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerModeRadioButton.isChecked()) {

                    nameFragment nameFragment = new nameFragment();
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_frame, nameFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    AInameFragment AInameFragment = new AInameFragment();
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_frame, AInameFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        return view;
    }
}