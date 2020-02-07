package com.example.assignment2mcdices;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class GameSettings extends AppCompatActivity {

    public static final String DICE_MESSAGE = "com.example.assignment2mcdices.dice_message";
    public static final String SIDE_MESSAGE = "com.example.assignment2mcdices.side_message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_settings);
        Button game_button_setting = findViewById(R.id.game_button_settings);
        Button add_dices = findViewById(R.id.button_dice_plus);
        Button add_sides = findViewById(R.id.button_sides_plus);
        Button remove_dices = findViewById(R.id.button_dice_minus);
        Button remove_sides = findViewById(R.id.button_sides_minus);

        playButton(game_button_setting);
        add_dice_button(add_dices);
        add_sides_button(add_sides);
        remove_dice_button(remove_dices);
        remove_sides_button(remove_sides);
    }

    int minDices = 1;
    int minSides = 3;
    int maxDice = 6;
    int maxSides = 20;
    int dices_count = 2;
    int sides_count = 6;



    //This function passes the values to the next activity and takes ur there
    public void playButton(Button play_button){
        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(GameSettings.this, GamePlay.class);
                intent.putExtra(DICE_MESSAGE ,dices_count);
                intent.putExtra(SIDE_MESSAGE,sides_count);
                startActivity(intent);
            }
        });
    }

    //This Function increments the no of dice
    public void add_dice_button(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dice_number = findViewById(R.id.dices_no);
                TextView status = findViewById(R.id.status);
                if(maxDice >= (dices_count + 1)) {
                    dices_count = dices_count + 1;
                    dice_number.setText("" + dices_count);
                }else{
                    //Out of bounds Error Case for the Maximum Dice
                    Context context = getApplicationContext();
                    CharSequence text = "Max Number of dice is "+maxDice;
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    status.setText("Max Number of dice is "+maxDice);
                }
            }
        });
    }

    //This Function increments the no of sides on the dice
    public void add_sides_button(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView sides_number = findViewById(R.id.sides_no);
                TextView status = findViewById(R.id.status);
                if(maxSides >= (sides_count + 1)) {
                    sides_count = sides_count + 1;
                    sides_number.setText("" + sides_count);
                }else{
                    //Out of bounds Error Case for the Maximum Sides
                    Context context = getApplicationContext();
                    CharSequence text = "Max Number of Sides is "+maxSides;
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    status.setText("Max Number of Sides is "+maxSides);
                }
            }
        });
    }

    //This Function reduces the number of dices
    public void remove_dice_button(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dice_number = findViewById(R.id.dices_no);
                TextView status = findViewById(R.id.status);
                if(minDices <= (dices_count - 1)){
                    dices_count = dices_count - 1;
                    dice_number.setText(""+dices_count);
                }else{
                    //Out of bounds Error Case for the Minimum Dice
                    Context context = getApplicationContext();
                    CharSequence text = "Minimum Number of Dices is "+minDices;
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    status.setText("Minimum Number of Dices is "+minDices);
                }

            }
        });
    }

    //This Function increments the no of sides
    public void remove_sides_button(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView status = findViewById(R.id.status);
                TextView sides_number = findViewById(R.id.sides_no);
                if(minSides <= (sides_count - 1)){
                    sides_count = sides_count - 1;
                    sides_number.setText(""+sides_count);
                }else{
                    //Out of bounds Error Case for the Minimum Dice
                    Context context = getApplicationContext();
                    CharSequence text = "Minimum Number of Sides is "+minSides;
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    status.setText("Minimum Number of Sides is "+minSides);
                }
            }
        });
    }
}
