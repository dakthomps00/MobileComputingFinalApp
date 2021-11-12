package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class changecontactact extends AppCompatActivity
{
    static ListView list;
    static ArrayList<String> contactname = new ArrayList<>();
    static String itemname;
    static String cname;
    static boolean donebe4 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changecontactact);

        //fills out contacts
        copycontacts();

        ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, contactname);

        list = findViewById(R.id.contactview);
        list.setAdapter (adapter);

        //allows you to click the listview options
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick (AdapterView<?> parent, View view, int position, long id)
            {
                itemname = (String)list.getItemAtPosition(position);
                Toast.makeText(changecontactact.this, itemname, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //fills out contacts, only when first loaded
    public void copycontacts()
    {
        boolean granted;

        if(donebe4 == false)
        {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
            {
                granted = true;
            }
            else
            {
                granted = false;
            }

            if (granted == true)
            {
                Cursor location = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

                while (location.moveToNext())
                {
                    cname = location.getString(location.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    cname = cname + ":  ";
                    cname = cname + location.getString(location.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactname.add(cname);
                }

                location.close();
                donebe4 = true;
            }
            else
            {
                Toast.makeText(this, "Accessing your contacts failed because you didn't give permission.", Toast.LENGTH_LONG).show();
            }
        }

    }

    //sends the contact chosen back along with the boolean to make sure onresume in main only happens after this activity occurs
    public void onChangeClick(View view)
    {
        boolean frmcntct = true;
        if (itemname == null)
        {
            Toast.makeText(this, "Please select a contact to change to.", Toast.LENGTH_LONG).show();
        }
        else {
            Intent messenger = new Intent(this, MainActivity.class);
            messenger.putExtra("CNTCT", itemname);
            messenger.putExtra("FROM", frmcntct);

            Toast.makeText(this, "Contact Changed.", Toast.LENGTH_SHORT).show();
            startActivity(messenger);
        }
    }
}