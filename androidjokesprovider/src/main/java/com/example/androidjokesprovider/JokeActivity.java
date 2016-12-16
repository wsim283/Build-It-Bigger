package com.example.androidjokesprovider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class JokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        if(getIntent().hasExtra(getString(R.string.joke_extra))){
            String joke = getIntent().getStringExtra(getString(R.string.joke_extra));
            TextView jokeTextView = (TextView)findViewById(R.id.jokeTextview);
            jokeTextView.setText(joke);
        }

    }
}
