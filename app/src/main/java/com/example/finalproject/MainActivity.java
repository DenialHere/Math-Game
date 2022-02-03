package com.example.finalproject;

import static java.lang.Math.round;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //The user inputted answer
    EditText answer;
    //The equation being generated
    TextView question;
    //The solution to the randomly generated equation
    int randomSolved;
    //The number of questions the user generated and solved
    int numberOfQuestions;
    //The number of right & wrong answers
    int numberRight, numberWrong;
    //All the equations the user generated and solved
    ArrayList<String> allEquations = new ArrayList<String>();
    //The full equation generated
    String fullEquation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void button_clicked_numbers(View view) {
        //This method is attached to all the options the user can enter 0-9, .-, etc.
        answer = findViewById(R.id.editTextTextAnswer);
        Button btn = (Button) view;
        //Since the text value of the buttons already contain the information we need we can
        //Grab the text value of the button pressed and inserting it into the answer textview
        answer.append(btn.getText());

    }

    public void button_clicked_options(View view) {
        //This method is for all the other options that doesn't include entering anything into the
        //text field such as clear,generate.etc
        answer = findViewById(R.id.editTextTextAnswer);
        //Grabbing the view of the button pressed
        switch (view.getId())
        {
            case R.id.buttonClear:
                answer.setText("");
                break;
            case R.id.buttonQuit:
                Toast.makeText(this, "Goodbye!", Toast.LENGTH_SHORT).show();
                System.exit(0);
                break;
            case R.id.buttonGenerate:
                random();
                break;
            case R.id.buttonEquals:
                try {
                    //Checking if the user clicked generate and has entered an answer
                    if(!question.getText().toString().equals("") || !answer.getText().toString().equals("")){
                        //If the answer is correct
                        if (answer.getText().toString().equals(String.valueOf(randomSolved))){
                            //We save the whole equation in an array list and format it for displaying later
                            allEquations.add(fullEquation + " = " + answer.getText()+ "\nYour answer is Correct \n-----------------------------------------\n");
                            //Incrementing the number of right answers for use in the results page
                            numberRight++;
                        }
                        else {
                            //We save the whole equation in an array list and format it for displaying later
                            allEquations.add(fullEquation + " = " + answer.getText() + "\nYour answer is Wrong!! \n-----------------------------------------\n");
                            //Incrementing the number of wrong answers for use in the results page
                            numberWrong++;
                        }
                    }
                    //If the user hasn't generated an equation or entered a answer show an error
                    else {
                        Toast.makeText(this, "Please click generate and enter the answer!", Toast.LENGTH_SHORT).show();
                    }
                    //Incrementing the total number of questions the user generated for use in the results page
                    numberOfQuestions++;
                    //Clearing the answer and question
                    answer.setText("");
                    question.setText("");
                }
                catch (Exception e){

                }
                break;

            case R.id.buttonShowAll:
                //Calculating the percentages
                Double percentageRight = (double) numberRight / numberOfQuestions * 100;
                Double percentageWrong = (double) numberWrong / numberOfQuestions * 100;
                //Formatting the percentages to be displayed in the results page
                String results = round(percentageRight) + "% Correct Answer\n" + round(percentageWrong) + "% Wrong Answer" ;

                //Preparing to transition to the results page
                Intent intent = new Intent(this, ResultActivity.class);
                Bundle bundle = new Bundle();
                //Passing all the equations generated and the results to the results page
                bundle.putStringArrayList("allEquations", allEquations);
                bundle.putString("results", results);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

        }

    }

    public void random(){
        //Generating a random Number
        Random rnd = new Random();
        //Generating two random numbers between 0-99
        int randomNumber1 = rnd.nextInt(99);
        int randomNumber2 = rnd.nextInt(99);
        //Your example included only addition but i decided to add all the signs

        //Generating another random number to determine what sign will be used
        int randomSign = rnd.nextInt(3);
        String sign = "";

        //With this switch case we are going to be determining the randomly generated sign &&
        //Solving the equation so that we will be able to compare with the user's answer
        switch (randomSign)
        {
            case 0:
                sign = "+";
                randomSolved = randomNumber1 + randomNumber2;
                break;
            case 1:
                sign = "-";
                randomSolved = randomNumber1 - randomNumber2;
                break;
            case 2:
                sign = "*";
                randomSolved = randomNumber1 * randomNumber2;
                break;
            case 3:
                sign = "/";
                randomSolved = randomNumber1 / randomNumber2;
                break;
        }
        //Formatting this into a string to be easily passed later
        fullEquation = (randomNumber1) + " " + sign + " " + (randomNumber2);
        //Setting the question to the randomly generated one
        question = findViewById(R.id.textViewQuestion);
        question.setText(fullEquation);
    }
}