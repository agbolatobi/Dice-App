package com.example.assignment2mcdices;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class GamePlay extends AppCompatActivity   {

    final List<dice> dice_list = new ArrayList<dice>();
    private static final int SHAKE_THRESHOLD = 800;
    private SensorManager sensorManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_play);
        Intent intent = getIntent();
        int dices = intent.getIntExtra(GameSettings.DICE_MESSAGE,2);
        int sides = intent.getIntExtra(GameSettings.SIDE_MESSAGE,6);
        Button roll_button = findViewById(R.id.roll_button);

        for(int i = 0;i < dices;i++){
            TextView newText;

            if(i==0){
                newText = findViewById(R.id.dice1);
            }else if(i==1){
                newText = findViewById(R.id.dice2);
            }else if(i==2){
                newText = findViewById(R.id.dice3);
            }else if(i==3){
                newText = findViewById(R.id.dice4);
            }else if(i==4){
                newText = findViewById(R.id.dice5);
            }else if(i==5){
                newText = findViewById(R.id.dice6);
            }else{
                newText = findViewById(R.id.dice6);
            }
            newText.setVisibility(View.GONE);
            Drawable background = getDrawable(R.drawable.empty_dice_box);
            newText.setBackground(background);
            dice Dice = new dice(sides,newText);
            dice_list.add(Dice);
        }
        String Reply = " ";
        LinearLayout layout = (LinearLayout) findViewById(R.id.dice_box);

        //sets the dice and draws them
        for(dice Dice : dice_list){
            Dice.getDice_view().setText(""+Dice.getValue());
            Drawable background = getDrawable(R.drawable.dicebox);
            Dice.getDice_view().setBackground(background);
            Dice.getDice_view().setVisibility(View.VISIBLE);
        }

        //Starts the Sensor object for shake detection
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Log.d("sensor","past");

        //Checks if the device has an accelerometer
        if (accelerometer != null){

            //Listener for the Sensory events
            final SensorEventListener env = new SensorEventListener() {
                long lastTime = 0;
                float last_x = 0;
                float last_y = 0;
                float last_z = 0;

                //Detects the shake event and calls the dice roll method
                @Override
                public void onSensorChanged(SensorEvent event) {
                    long curTime = System.currentTimeMillis();
                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];

                    long diffTime = (curTime - lastTime);
                    if(diffTime > 200){
                        lastTime = curTime;
                        float speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;

                        if (speed > SHAKE_THRESHOLD) {
                            Log.d("sensor", "shake detected w/ speed: " + speed);
                            rollDice();
                        }
                        last_x = x;
                        last_y = y;
                        last_z = z;
                    }

                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }

                protected void onPause() {
                    sensorManager.unregisterListener(this);
                    GamePlay.super.onPause();
                }

                protected void onResume() {
                    GamePlay.super.onResume();
                    sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),sensorManager.SENSOR_DELAY_NORMAL);
                }

                };
            sensorManager.registerListener(env,accelerometer,20);

        } else {
            //Error Case for no accelerometer
        }

        //Listens for the button clicked even to roll the dices
        roll_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();
            }
        });

    }

    //This Function Rolls the Dice, Plays the Music and Loads the Animations
    public void rollDice(){
        int total = 0;
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.dice_sound);
        mediaPlayer.start();
        for(dice Dice : dice_list){
            Dice.roll();
            total = total+Dice.getValue();
            Dice.getDice_view().startAnimation(AnimationUtils.loadAnimation(this,R.anim.shake));
            Dice.getDice_view().setText(""+Dice.getValue());
            TextView totalView = findViewById(R.id.total_value);
            totalView.setText(total+"");
        }

    }



}
