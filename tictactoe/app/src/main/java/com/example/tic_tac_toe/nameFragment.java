package com.example.tic_tac_toe;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class nameFragment extends Fragment {

    private EditText pname1,pname2,rounds;
    private RadioButton gameModeRadioButton;
    private RadioGroup radioGroup;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment's layout
        View view = inflater.inflate(R.layout.fragment_name, container, false);

        // Initialize your views here
        pname1 = view.findViewById(R.id.pname1);
        pname2 = view.findViewById(R.id.pname2);
        rounds = view.findViewById(R.id.rounds);
        radioGroup = view.findViewById(R.id.radioGroup);

        Button button = view.findViewById(R.id.button);
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Pname1 = pname1.getText().toString();
                String Pname2 = pname2.getText().toString();
                String Rounds = rounds.getText().toString();



                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

                if (selectedRadioButtonId == R.id.p3) {
                    // 3x3 game mode selected
                    gameFragment gameFragment = new gameFragment();
                    Bundle args = new Bundle();
                    args.putStringArray("playerNames", new String[]{Pname1, Pname2, Rounds});
                    gameFragment.setArguments(args);

                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_frame, gameFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else if (selectedRadioButtonId == R.id.p4) {
                    // 4x4 game mode selected
                    game4x4Fragment game4x4Fragment = new game4x4Fragment();
                    Bundle args = new Bundle();
                    args.putStringArray("playerNames", new String[]{Pname1, Pname2, Rounds});
                    game4x4Fragment.setArguments(args);

                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_frame, game4x4Fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else if (selectedRadioButtonId == R.id.p5) {
                    // 5x5 game mode selected
                    game5x5Fragment game5x5Fragment = new game5x5Fragment();
                    Bundle args = new Bundle();
                    args.putStringArray("playerNames", new String[]{Pname1, Pname2, Rounds});
                    game5x5Fragment.setArguments(args);

                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_frame, game5x5Fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
        return view;
    }
}
