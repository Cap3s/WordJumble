package com.example.wordjumble;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText word,clue;
    private TextView bestScore;
    private int highScore,new_highScore;

    Animation scaleUp,scaleDown;
    private static final String FILE_NAME = "highscore.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scaleup);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scaledown);

        Intent intent = getIntent();
        highScore = intent.getIntExtra("BestScore",0);
        bestScore = findViewById(R.id.bestScore);

        if (highScore>new_highScore) {
            new_highScore =highScore;
            bestScore.setText("Best Score : " + new_highScore);
            save(bestScore);
        }
        load();
        word = findViewById(R.id.editWord);
        clue = findViewById(R.id.editClue);

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                button.startAnimation(scaleUp);
                button.startAnimation(scaleDown);

                if((word.getText().toString().length()>0)&&(clue.getText().toString().length()>0)) {
                    openCasual();
                }
                else{
                    Toast.makeText(MainActivity.this, "Enter Word and Clue", Toast.LENGTH_SHORT).show();
                }}
            });
        }

    public void openCasual(){

        Bundle bundle = new Bundle();
        bundle.putString("str1",word.toString());
        bundle.putString("str2",clue.toString());

        //Second Activity
        Intent intent = new Intent(MainActivity.this,Casual.class);
        Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();

        intent.putExtra("str1",word.getText().toString());
        intent.putExtra("str2",clue.getText().toString());
        startActivity(intent,b);

    }

    public void save(View v) {

        String string;
        string = bestScore.getText().toString();
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fileOutputStream.write(string.getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void load(){
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String text;

            while((text = bufferedReader.readLine()) != null ){
                stringBuilder.append(text).append("\n");
            }

            bestScore.setText(stringBuilder.toString());

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if (fileInputStream != null){
                try{
                    fileInputStream.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

}