package com.example.assignment2mcdices;

import android.widget.TextView;

public class dice {
    private int no_of_sides = 6; //sets default number of sides
    private int value = 1; //default dice value
    TextView dice_view; //dice text view element

    //Dice constructor
    dice(int sides, TextView view){
        no_of_sides = sides;
        dice_view = view;
    }

    //generates random dice vale
    public void roll(){
        value = (int)(Math.random()*((no_of_sides-1)+1))+1;
    }

    //gets the number of sides on the dice
    public int getNo_of_sides() {
        return no_of_sides;
    }

    //gets dice value
    public int getValue() {
        return value;
    }

    //get the dice view
    public TextView getDice_view() {
        return dice_view;
    }

    //sets the dice view
    public void setDice_view(TextView dice_view) {
        this.dice_view = dice_view;
    }
}
