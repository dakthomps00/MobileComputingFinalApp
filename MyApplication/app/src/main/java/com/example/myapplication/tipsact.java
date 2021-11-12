package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class tipsact extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipsact);

        //the options for tabs, send you to each activity depending on which tab you tapped
        Intent home = new Intent (this, MainActivity.class);
        Intent video = new Intent (this, videoact.class);
        Intent resource = new Intent (this, resourceact.class);

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
                        startActivity(video);
                        break;
                    case R.id.action_Resources:
                        startActivity(resource);
                        break;
                    case R.id.action_Tips:
                        break;
                }
                return true;
            }
        });

        //makes sure current activity is greyed out
        bottomNavigationView.setSelectedItemId(R.id.action_Tips);
    }

    //all of these send the title and message depending on what button was clicked in the what2do activity
    public void onFollowClick (View view)
    {
        Intent follow = new Intent(this, what2doact.class);
        follow.putExtra("TITLE", "What to do if you are being followed:");
        String message = "If you suspect you are being followed, remain calm.\n" +
                "Take a detour such as going around the block in a circle or crossing the street and then crossing again a few minutes later.\n" +
                "If they continue to follow you, seek help.\n" +
                "Do not continue home or somewhere where you are not in view such as an alley.\n" +
                "Please go to somewhere very public or that has security such as a 24 hour supermarket or a busy restaurant.\n" +
                "Once you are safe to do so, call the authorities.";
        follow.putExtra("MESSAGE", message);
        startActivity(follow);
    }

    public void onRageClick (View view)
    {
        Intent rage = new Intent(this, what2doact.class);
        rage.putExtra("TITLE", "What to do if you are experiencing a road rage incident:");
        String message = "If you suspect you are being followed, remain calm.\n" +
                "Do not react, this can escalate the other person's anger.\n" +
                "If they follow you, attempt to drive normally. Do NOT stop. Do not leave the vehicle.\n" +
                "If you must stop for traffic, try to leave room for a quick getaway.\n" +
                "If you still feel threatened, call the authorities and drive to somewhere very public or to a police station.\n";
        rage.putExtra("MESSAGE", message);
        startActivity(rage);
    }

    public void onStalkClick (View view)
    {
        Intent stalk = new Intent(this, what2doact.class);
        stalk.putExtra("TITLE", "What to do if you are being stalked:");
        String message = "If you suspect you are being followed, remain calm.\n" +
                "Do not initiate contact with your stalker, but do not ignore them/what they are doing.\n" +
                "Be sure to increase security measures around your home such as locks, alarms, and cameras.\n" +
                "Alert your loved ones to your situation and the threat so they may also be vigilant on your behalf.\n" +
                "Be sure to keep every piece of evidence, broken items, emails, call logs, messages, etc.\n" +
                "Give this evidence to the authorities to intervene.";
        stalk.putExtra("MESSAGE", message);
        startActivity(stalk);
    }

    public void onDrugClick (View view)
    {
        Intent drug = new Intent(this, what2doact.class);
        drug.putExtra("TITLE", "What to do if you suspect you have been drugged:");
        String message = "Look for a friend or someone trustworthy and tell them you are sick in any way you can, whether verbally or otherwise.\n" +
                "Get them to take you to the hospital or contact emergency services.\n" +
                "Do not leave by yourself if possible, you may lose consciousness.\n" +
                "If you feel as if or know you have been you have been assaulted, go to a hospital to get a drug test within 24 to 72 hours.\n";
        drug.putExtra("MESSAGE", message);
        startActivity(drug);
    }

    public void onInjuryClick (View view)
    {
        Intent injury = new Intent(this, what2doact.class);
        injury.putExtra("TITLE", "What to do if you or someone else has been injured:");
        String message = "Make sure you or the other person are not in imminent danger and remove the danger if possible before taking any other steps.\n" +
                "When it is safe to do so, contact emergency services if needed.\n" +
                "Deliver basic first aid at this point (i.e. putting pressure on a bleeding wound, cleaning a wound out, etc.).\n" +
                "\n" +
                "If someone is unconscious but breathing, with no other injury that would not allow you to move them, place them in recovery position.\n" +
                "(Roll the person onto their side and open their airway by tilting their head back by gently moving their chin up.)\n" +
                "Continue to monitor them while help is on the way.\n" +
                "\n" +
                "If someone is unconscious and NOT breathing normally, please call emergency services as soon as possible.\n" +
                "Perform chest compressions to the beat of Stayin' Alive by the Bee Gees in the middle of their chest.\n" +
                "If you are trained in CPR, please perform it was you were trained until help arrives.";
        injury.putExtra("MESSAGE", message);
        startActivity(injury);
    }

    public void onSuggestionClick (View view)
    {
        Intent sugg = new Intent(this, what2doact.class);
        sugg.putExtra("TITLE", "dakotasthompson@gmail.com");
        String message = "Please contact me at the above email if you have any suggestions for the app to make it better or find a bug! :)";
        sugg.putExtra("MESSAGE", message);
        startActivity(sugg);
    }

    //fills the menu aka tab at bottom
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }
}