package com.example.cs2340cteam50game;

import androidx.appcompat.app.AppCompatActivity;

import android.media.PlaybackParams;
import android.media.tv.TimelineRequest;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Console;
import java.util.Timer;
import java.util.TimerTask;

public class GameScreen extends AppCompatActivity {

    private int health;
    private String name;
    private int difficulty;
    private String difficultyLabel;
    private int spriteNum;
    private int scoreVal;

    private int currentScreen = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_game_activity);

        //getting ids for different elements that will be needed
        Button skipToEnd = (Button) findViewById(R.id.skipToEnd);
        TextView healthValue = (TextView) findViewById(R.id.healthValue);
        TextView playerName = (TextView) findViewById(R.id.playerName);
        TextView difficultySetting = (TextView) findViewById(R.id.difficultySetting);
        ImageView playerSprite = (ImageView) findViewById(R.id.playerSprite);

        TextView scoreText = (TextView) findViewById(R.id.scoreText);
        ImageView map = (ImageView) findViewById(R.id.gameMap);
        Button previousMap = (Button) findViewById(R.id.previousMap);
        Button nextMap = (Button) findViewById(R.id.nextMap);

        PlayerClass player = PlayerClass.getPlayer();
        name = player.getUsername();
        difficulty = player.getDifficultyNum();
        spriteNum = player.getSpriteNum();
        health = player.getHealthPoints();

        switch (difficulty) {
        case 1:
            difficultyLabel = "Easy";
            break;
        case 2:
            difficultyLabel = "Medium";
            break;
        case 3:
            difficultyLabel = "Hard";
            break;
        default:
            break;
        }

        playerName.setText(name);
        healthValue.setText(Integer.toString(health));
        difficultySetting.setText(difficultyLabel);

        switch (spriteNum) {
        case 1:
            playerSprite.setImageResource(R.drawable.red_idle);
            break;
        case 2:
            playerSprite.setImageResource(R.drawable.blue_idle);
            break;
        case 3:
            playerSprite.setImageResource(R.drawable.green_idle);
            break;
        default:
            break;
        }

        scoreText.setText("Score: 50");
        new CountDownTimer((50) * 3000, 1000) {
            public void onTick(long millisUntilFinished) {
                scoreVal = (int) (millisUntilFinished / 1000);
                scoreText.setText("Score: " + scoreVal);
            }
            public void onFinish() {
                scoreText.setText("Score: 0");
            }
        }.start();

        //skip to end screen when button pressed
        skipToEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Score score = new Score(name, scoreVal);
                Leaderboard leaderBoard = Leaderboard.getLeaderboard();
                leaderBoard.addScore(score);
                //System.out.println(score.getScore());
                Intent intent = new Intent(GameScreen.this, EndScreen.class);
                intent.putExtra("score", score.getScore());
                startActivity(intent);
            }
        });

        nextMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentScreen++;
                switch(currentScreen) {
                    case 0:
                        map.setImageResource(R.drawable.map1);
                        break;
                    case 1:
                        map.setImageResource(R.drawable.map2);
                        break;
                    case 2:
                        map.setImageResource(R.drawable.map3);
                        break;
                    default:
                        map.setImageResource(R.drawable.map1);
                        currentScreen = 0;
                        break;
                }
            }
        });

        previousMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentScreen--;
                switch (currentScreen) {
                    case 0:
                        map.setImageResource(R.drawable.map1);
                        break;
                    case 1:
                        map.setImageResource(R.drawable.map2);
                        break;
                    case 2:
                        map.setImageResource(R.drawable.map3);
                        break;
                    default:
                        map.setImageResource(R.drawable.map1);
                        currentScreen = 0;
                        break;
                }
            }
        });

    }
}