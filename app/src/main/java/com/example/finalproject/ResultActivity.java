package com.example.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        TextView equations = findViewById(R.id.textViewEquations);
        TextView result = findViewById(R.id.textViewResults);

        //Grabbing the data passed by last activity
        Bundle bundle = getIntent().getExtras();
        //Creating a new array list to store the data passed
        ArrayList<String> allEquations = new ArrayList<String>();
        //Passing the data from previous activity
        allEquations = bundle.getStringArrayList("allEquations");
        String results = bundle.getString("results");

        //Converting array list to string so we can format
        String list = allEquations.toString();
        //When displaying an array list we get [] and , after each so we are going remove them
        list = list.replace("[", "");
        list = list.replace("]", "");
        list = list.replace(",", "");
        //Setting the textviews to the data we grabbed
        equations.setText(list);
        result.setText(results);
    }

    public void back(View view){
        finish();
    }
}
