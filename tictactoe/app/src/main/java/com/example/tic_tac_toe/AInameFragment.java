package com.example.tic_tac_toe;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class AInameFragment extends Fragment {

    private EditText pname ,rounds;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_iname, container, false);


        pname = view.findViewById(R.id.pname);
        rounds = view.findViewById(R.id.rounds);


        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Pname = pname.getText().toString();
                String Rounds = rounds.getText().toString();


                AIgameFragment AIgameFragment = new AIgameFragment();

                Bundle args = new Bundle();
                args.putStringArray("playerNames", new String[]{Pname,Rounds});
                AIgameFragment.setArguments(args);

                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame, AIgameFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}