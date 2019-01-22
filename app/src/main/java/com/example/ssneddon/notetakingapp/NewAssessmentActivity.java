package com.example.ssneddon.notetakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewAssessmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_assessment);

        getSupportActionBar().setTitle("Create an Assessment"); // set action bar title

    }
}
