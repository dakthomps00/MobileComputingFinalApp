package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class what2doact extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what2doact);
    }

    //fills the screen every time, onResume is called when onCreate is the first time
    public void onResume()
    {
        super.onResume();
        createscreen();
    }

    //takes what came from the tips activity and fills the textviews
    public void createscreen()
    {
        TextView title = findViewById(R.id.ifx);
        TextView message = findViewById(R.id.doy);
        Intent sent = getIntent();

        String title2 = sent.getStringExtra("TITLE");
        String message2 = sent.getStringExtra("MESSAGE");

        title.setText(title2);
        message.setText(message2);
    }
}