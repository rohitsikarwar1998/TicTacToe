package com.example.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    private Button[][] buttons=new Button[3][3];

    private int roundCount=0;

    private int player1Points=0;
    private  int player2Points=0;

    private TextView player1;
    private TextView player2;

    private boolean player1Turn=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1=findViewById(R.id.text_view_p1);
        player2=findViewById(R.id.text_view_p2);

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                String buttonid="button_"+i+j;
                int resID=getResources().getIdentifier(buttonid,"id",getPackageName());
                buttons[i][j]=findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button resetButton=findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                player1Points=0;
                player2Points=0;
                player1.setText("Player 1 : "+player1Points);
                player2.setText("Player 2 : "+player2Points);
                resetBoard();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        if(player1Turn){
            ((Button)v).setText("X");
        }
        else ((Button)v).setText("O");

        roundCount++;

        if(checkWins()){
            if(player1Turn) player1Win();
            else player2Win();
        }
        else if(roundCount==9) draw();

        else {
            player1Turn=!player1Turn;
        }
    }

    private boolean checkWins()
    {
        String[][] result=new String[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                result[i][j]=buttons[i][j].getText().toString();
            }
        }

        for(int i=0;i<3;i++)
        {
            if(result[i][0].equals(result[i][1])&&result[i][1].equals(result[i][2])
            &&!result[i][0].equals("")){
                return true;
            }
        }

        for(int i=0;i<3;i++)
        {
            if(result[0][i].equals(result[1][i])&&result[1][i].equals(result[2][i])
                    &&!result[0][i].equals("")){
                return true;
            }
        }
        if(result[0][0].equals(result[1][1])&&result[1][1].equals(result[2][2])
                &&!result[0][0].equals("")) return true;

        if(result[0][2].equals(result[1][1])&&result[1][1].equals(result[2][0])
                &&!result[0][2].equals("")) return true;

        return false;
    }

    private void player1Win(){
        player1Points++;
        player1.setText("Player 1 : "+player1Points);
        Toast.makeText(this,"Player 1 wins!",Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void player2Win(){
        player2Points++;
        player2.setText("Player 2 : "+player2Points);
        Toast.makeText(this,"Player 2 wins!",Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void draw(){
        Toast.makeText(this,"It's a Draw!",Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void resetBoard(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                buttons[i][j].setText("");
            }
        }

        roundCount=0;
        player1Turn=true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState,outPersistentState);
        outState.putInt("roundCount",roundCount);
        outState.putInt("player1Points",player1Points);
        outState.putInt("player2Points",player2Points);
        outState.putBoolean("player1Turn",player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount=savedInstanceState.getInt("roundCount");
        player1Points=savedInstanceState.getInt("player1Points");
        player2Points=savedInstanceState.getInt("player2Points");
        player1Turn=savedInstanceState.getBoolean("player1Turn");
    }
}
