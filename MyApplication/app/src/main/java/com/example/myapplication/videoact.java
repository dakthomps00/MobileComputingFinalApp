package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class videoact extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoact);

        //the options for tabs, send you to each activity depending on which tab you tapped
        Intent home = new Intent (this, MainActivity.class);
        Intent resource = new Intent (this, resourceact.class);
        Intent tips = new Intent (this, tipsact.class);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.action_Home:
                        startActivity(home);
                        break;
                    case R.id.action_Videos:
                        break;
                    case R.id.action_Resources:
                        startActivity(resource);
                        break;
                    case R.id.action_Tips:
                        startActivity(tips);
                        break;
                }
                return true;
            }
        });

        //makes sure current activity is greyed out
        bottomNavigationView.setSelectedItemId(R.id.action_Videos);
    }

    //fills the menu aka tab at bottom
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }
}