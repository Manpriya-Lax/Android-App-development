package com.example.tic_tac_toe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class game5x5Fragment extends Fragment implements View.OnClickListener {
    private boolean playerOneActive = true;
    private TextView playerOneScore, playerTwoScore ,playerStatus ,player1,player2 ,PlayerStatus,f_winner;
    private String pname1, pname2, rounds;
    private Button reset, playagain;
    private Button[] buttons = new Button[25];
    private int playerOneScoreCount, playerTwoScoreCount,Rounds,roundcount,roundvalue;
    private int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,2,2,2,2,2,2,2,2,2};
    private int[][] winningPositions = {
            {0, 1, 2, 3, 4}, {5, 6, 7, 8, 9}, {10, 11, 12, 13, 14}, {15, 16, 17, 18, 19}, {20, 21, 22, 23, 24},
            {0, 5, 10, 15, 20}, {1, 6, 11, 16, 21}, {2, 7, 12, 17, 22}, {3, 8, 13, 18, 23}, {4, 9, 14, 19, 24},
            {0, 6, 12, 18, 24}, {4, 8, 12, 16, 20}
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game5x5, container, false);

        playerOneScore = view.findViewById(R.id.score_Player1);
        playerTwoScore = view.findViewById(R.id.score_Player2);
        PlayerStatus =   view.findViewById(R.id.PlayerStatus);
        f_winner =   view.findViewById(R.id.f_winner);

        buttons[0] = view.findViewById(R.id.btn0);
        buttons[1] = view.findViewById(R.id.btn1);
        buttons[2] = view.findViewById(R.id.btn2);
        buttons[3] = view.findViewById(R.id.btn3);
        buttons[4] = view.findViewById(R.id.btn4);
        buttons[5] = view.findViewById(R.id.btn5);
        buttons[6] = view.findViewById(R.id.btn6);
        buttons[7] = view.findViewById(R.id.btn7);
        buttons[8] = view.findViewById(R.id.btn8);
        buttons[9] = view.findViewById(R.id.btn9);
        buttons[10] = view.findViewById(R.id.btn10);
        buttons[11] = view.findViewById(R.id.btn11);
        buttons[12] = view.findViewById(R.id.btn12);
        buttons[13] = view.findViewById(R.id.btn13);
        buttons[14] = view.findViewById(R.id.btn14);
        buttons[15] = view.findViewById(R.id.btn15);
        buttons[16] = view.findViewById(R.id.btn16);
        buttons[17] = view.findViewById(R.id.btn17);
        buttons[18] = view.findViewById(R.id.btn18);
        buttons[19] = view.findViewById(R.id.btn19);
        buttons[20] = view.findViewById(R.id.btn20);
        buttons[21] = view.findViewById(R.id.btn21);
        buttons[22] = view.findViewById(R.id.btn22);
        buttons[23] = view.findViewById(R.id.btn23);
        buttons[24] = view.findViewById(R.id.btn24);


        playerStatus = view.findViewById(R.id.PlayerStatus);
        playagain = view.findViewById(R.id.playagain);
        reset = view.findViewById(R.id.reset);

        for (int i = 0; i < buttons.length; i++) {
            int buttonId = getResources().getIdentifier("btn" + i, "id", getActivity().getPackageName());
            buttons[i] = view.findViewById(buttonId);
            buttons[i].setOnClickListener(this);
        }

        Bundle arguments = getArguments();
        if (arguments != null) {
            String[] playerNames = arguments.getStringArray("playerNames");
            if (playerNames != null && playerNames.length >= 3) {
                pname1 = playerNames[0];
                pname2 = playerNames[1];
                rounds = playerNames[2];

                player1 = view.findViewById(R.id.player1);
                player2 = view.findViewById(R.id.player2);

                player1.setText(pname1);
                player2.setText(pname2);

                Rounds = Integer.parseInt(rounds);



            }
        }

        return view;
    }

    @Override
    public void onClick(View view) {

        if (!((Button) view).getText().toString().equals("")) {
            return;
        } else if (checkWinner()) {
            return;
        }
        String buttonID = view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1, buttonID.length()));

        if (playerOneActive) {
            ((Button) view).setText("X");
            gameState[gameStatePointer] = 0;
        } else {
            ((Button) view).setText("O");
            gameState[gameStatePointer] = 1;
        }
        roundcount++;

        if (checkWinner()) {
            if (playerOneActive) {
                playerOneScoreCount++;
                PlayerStatus.setText("Player 1 win");
                updatePlayerScore();
                roundvalue++;

            } else {
                playerTwoScoreCount++;
                PlayerStatus.setText("Player 2 win");
                updatePlayerScore();
                roundvalue++;
            }

        } if (roundcount == 25) {

            playerStatus.setText("No Winner");
            roundvalue++;

        } else {

            playerOneActive = !playerOneActive;

        }

        if (Rounds==roundvalue) {
            if (playerOneScoreCount>playerTwoScoreCount){
                f_winner.setText("Player 1 win");
            } else if
            (playerOneScoreCount<playerTwoScoreCount){
                f_winner.setText("Player 2 win");
            } else {
                f_winner.setText("No winner");
            }


        }


        reset.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                playAgain();

                playerOneScoreCount= 0;
                playerTwoScoreCount= 0;
                PlayerStatus.setText("");
                f_winner.setText("");

                updatePlayerScore();

            }

        });
        playagain.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                playAgain();

            }

        });


    }

    private void updatePlayerScore() {
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    private void playAgain()
    {
        roundcount = 0;
        playerOneActive = true;
        for (int i = 0; i < buttons.length; i++) {
            gameState[i] = 2;
            buttons[i].setText("");
        }

    }

    private boolean checkWinner() {
        boolean winnerResults = false;
        for (int[] winningPositions : winningPositions) {
            if (gameState[winningPositions[0]] == gameState[winningPositions[1]] &&
                    gameState[winningPositions[1]] == gameState[winningPositions[2]] &&
                    gameState[winningPositions[2]] == gameState[winningPositions[3]] &&
                    gameState[winningPositions[3]] == gameState[winningPositions[4]] &&
                    gameState[winningPositions[0]] != 2)
            {
                winnerResults = true;
            }
        }

        return winnerResults;
    }

}
