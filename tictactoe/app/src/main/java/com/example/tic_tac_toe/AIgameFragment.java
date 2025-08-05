package com.example.tic_tac_toe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;
import androidx.fragment.app.Fragment;

public class AIgameFragment extends Fragment implements View.OnClickListener {
    private boolean playerOneActive = true;
    private TextView playerOneScore, playerTwoScore ,playerStatus ,player1,player2 ,PlayerStatus,f_winner;
    private String pname1, pname2, rounds;
    private Button reset, playagain;
    private Button[] buttons = new Button[9];
    private int playerOneScoreCount, playerTwoScoreCount,Rounds,roundcount,roundvalue, pmove=0, aimove=0;
    private int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    private int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6},

            {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_igame, container, false);

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

        playerStatus = view.findViewById(R.id.PlayerStatus);
        playagain = view.findViewById(R.id.playagain);
        reset = view.findViewById(R.id.reset);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setOnClickListener(this);
        }

        Bundle arguments = getArguments();
        if (arguments != null) {
            String[] playerNames = arguments.getStringArray("playerNames");
            if (playerNames != null && playerNames.length >= 2) {
                pname1 = playerNames[0];
                rounds = playerNames[1];

                player1 = view.findViewById(R.id.player1);
                player1.setText(pname1);
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


        int randomButton = getRandomButton();
        while (!((Button) buttons[randomButton]).getText().toString().equals("")) {
            // If the selected button is already marked, try again
            randomButton = getRandomButton();
        }
        if (playerOneActive ) {
            ((Button) view).setText("X");
            gameState[gameStatePointer] = 0;
            pmove++;

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

        } else if (roundcount == 9) {

            playerStatus.setText("No Winner");
            roundvalue++;

        } else {

            playerOneActive = !playerOneActive;
            makeAIMove();

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
        for (int i=0; i<buttons.length; i++)
        {
            gameState[i] = 2;
            buttons[i].setText("");
        }

    }

    private boolean checkWinner()

    {
        boolean winnerResults  = false;
        for (int[] winningPositions: winningPositions)
        {
            if(gameState[winningPositions[0]]==gameState[winningPositions[1]]&&
                    gameState[winningPositions[1]]==gameState[winningPositions[2]] &&
                    gameState[winningPositions[0]]!=2)
            {
                winnerResults = true;
            }
        }

        return winnerResults;
    }
    private void makeAIMove() {
        int randomButton = getRandomButton();
        while (!((Button) buttons[randomButton]).getText().toString().equals("")) {
            // If the selected button is already marked, try again
            randomButton = getRandomButton();
        }

        ((Button) buttons[randomButton]).setText("O");
        gameState[randomButton] = 1;
        aimove++;
    }
    private int getRandomButton() {
        return new Random().nextInt(10); // Generates a random number between 0 and 9.
    }

}
