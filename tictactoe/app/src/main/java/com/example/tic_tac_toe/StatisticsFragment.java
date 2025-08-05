package com.example.tic_tac_toe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class StatisticsFragment extends Fragment {


    public int playerOneScoreCount,playerTwoScoreCount,roundvalue,draw , P1P, P2P;
    private TextView p1w, p2w,p1l, p2l,p1d,p2d,p1p,p2p,back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        Bundle arguments = getArguments();
        if (arguments != null) {
            int[] playerNames = arguments.getIntArray("playerNames");
            if (playerNames != null && playerNames.length >= 3) {
                playerOneScoreCount = playerNames[0];
                playerTwoScoreCount = playerNames[1];
                roundvalue = playerNames[2];

            }
        }

        draw =roundvalue-playerOneScoreCount-playerTwoScoreCount ;
        P1P=(int) (playerOneScoreCount/roundvalue)*100;
        P1P= (int)(playerTwoScoreCount/roundvalue)*100;

        p1w =view.findViewById(R.id.p1w);
        p1d =view.findViewById(R.id.p1d);
        p2w =view.findViewById(R.id.p2w);
        p2d =view.findViewById(R.id.p2d);
        p1l =view.findViewById(R.id.p1l);
        p2l =view.findViewById(R.id.p2l);
        p1p =view.findViewById(R.id.p1p);
        p2p =view.findViewById(R.id.p2p);
        back=view.findViewById(R.id.back);

        p1w.setText(String.valueOf(playerOneScoreCount));
        p2w.setText(String.valueOf(playerTwoScoreCount));
        p1l.setText(String.valueOf(playerTwoScoreCount));
        p2l.setText(String.valueOf(playerOneScoreCount));
        p1d.setText(String.valueOf(draw));
        p2d.setText(String.valueOf(draw));
        p1p.setText(String.valueOf(P1P));
        p2p.setText(String.valueOf(P2P));


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gameFragment gameFragment = new gameFragment();
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame, gameFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        return view;




    }
}