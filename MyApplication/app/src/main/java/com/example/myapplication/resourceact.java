package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class resourceact extends AppCompatActivity {

    static String url;
    static ListView sitelist;
    databasehelper listdbh;
    Cursor listcursor;
    String[] itemstring;
    String nameurl[][];
    ArrayAdapter<String> listadapter;
    ArrayList<String> itemlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resourceact);

        //the options for tabs, send you to each activity depending on which tab you tapped
        Intent home = new Intent(this, MainActivity.class);
        Intent video = new Intent(this, videoact.class);
        Intent tips = new Intent(this, tipsact.class);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_Home:
                        startActivity(home);
                        break;
                    case R.id.action_Videos:
                        startActivity(video);
                        break;
                    case R.id.action_Resources:
                        break;
                    case R.id.action_Tips:
                        startActivity(tips);
                        break;
                }
                return true;
            }
        });

        //makes sure current activity is greyed out
        bottomNavigationView.setSelectedItemId(R.id.action_Resources);

        listdbh = new databasehelper(this);
    }

    //fills out listview from the database created
    public void onResume()
    {
        super.onResume();
        sitelist = (ListView)findViewById(R.id.siteview);
        // cursor point to data
        listcursor = listdbh.getcursor();

        // move to top
        // rtrn t or f
        // false = f in the chat
        listcursor.moveToFirst();

        // !!!!!!! needs testing
        if (!listcursor.moveToFirst())
        {
            Toast.makeText(this, "No Items.", Toast.LENGTH_LONG).show();
        }
        else
        {
            // yay items
            itemstring = new String[listcursor.getCount()];
            nameurl = new String [itemstring.length][2];
            for(int i=0;i<itemstring.length;i++)
            {
                String splitup[] = listcursor.getString(1).split(">");

                nameurl[i][0] = splitup[0];
                nameurl[i][1] = splitup[1];
                itemstring[i] = splitup[0];
                listcursor.moveToNext();
            }

            //sitelist needs 2b filled
            sitelist = findViewById(R.id.siteview);
            itemlist = new ArrayList<String>(Arrays.asList(itemstring));
            listadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemlist);
            sitelist.setAdapter(listadapter);
        }

        //allows you to choose what page to go to
        sitelist.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                url = (String)sitelist.getItemAtPosition(position);
                Toast.makeText(resourceact.this, url, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //sends you to your browser with the link
    public void onGoClick(View view)
    {
        if(url == null)
        {
            Toast.makeText(this, "You need to select a resource to go to it.", Toast.LENGTH_LONG).show();
        }
        else {
                int j = 0;
                while (!url.equals(nameurl[j][0]))
                {
                    j++;
                }

                String blah = nameurl[j][1];

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(blah));
                startActivity(i);

        }
    }

    //fills the menu aka tab at bottom
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }
}