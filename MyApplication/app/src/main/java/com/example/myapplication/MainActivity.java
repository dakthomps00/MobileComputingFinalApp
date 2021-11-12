package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.BreakIterator;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener
{
    private static final String TAG = "MainActivity";
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    public static String contactname;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    public static double latitude;
    public static double longitude;
    public static boolean frmcntct;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 100);

        //the options for tabs, send you to each activity depending on which tab you tapped
        Intent video = new Intent (this, videoact.class);
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
                        break;
                    case R.id.action_Videos:
                        startActivity(video);
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
        bottomNavigationView.setSelectedItemId(R.id.action_Home);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mLocationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
    }

    //on resume, get the contact from change contact activity, ONLY IF COMING FROM THAT ACTIVITY
    public void onResume()
    {
        super.onResume();
        Intent sent = getIntent();
        frmcntct = sent.getBooleanExtra("FROM", frmcntct);

        if (frmcntct == true)
        {
            contactname = sent.getStringExtra("CNTCT");
            frmcntct = false;
        }
    }

    //fills the menu aka tab at bottom
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

    //when you click call, call the contact named or tell them to pick a contact
    public void onCallClick(View view)
    {
        Intent callmessenger;

        if(contactname == null)
        {
            Toast.makeText(this, "You need to select a contact to call. :(", Toast.LENGTH_LONG).show();
        }
        else
        {
            String namenumb[] = contactname.split(":  ");

            callmessenger = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + namenumb[1]));
            startActivity(callmessenger);
        }
    }

    //when you click text, text the contact named or tell them to pick a contact
    //also asks for location permission in order to send the person's location in the text
    public void onTextClick(View view)
    {
        boolean granted;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            granted = true;
        }
        else
        {
            granted = false;
        }

        boolean granted2;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            granted2 = true;
        }
        else
        {
            granted2 = false;
        }


        if ((granted == true) && (granted2 == true))
        {
            Intent textmessenger;

            if(contactname == null)
            {
                Toast.makeText(this, "You need to select a contact to text. :(", Toast.LENGTH_LONG).show();
            }
            else
            {
                String namenumb[] = contactname.split(":");

                textmessenger = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + namenumb[1]));
                textmessenger.putExtra("sms_body", "Hello, I feel unsafe. Please contact me as soon as possible. Latitude: " + latitude + ", Longitude: " + longitude + ".");
                startActivity(textmessenger);
            }
        }
        else
        {
            Toast.makeText(this, "Please allow access to your location in order to send it to your emergency contact.", Toast.LENGTH_LONG);
        }
    }

    //when you click emergency services button, calls 911
    public void onEmergencyClick(View view)
    {
        Intent callemg;

        callemg = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "911"));
        startActivity(callemg);
    }

    //sends you to set your emergency contact
    public void onContactClick(View view)
    {
        Intent emergency = new Intent (this, changecontactact.class);
        startActivity(emergency);
    }

    //this came from an online tutorial
    //https://demonuts.com/android-get-current-location/

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startLocationUpdates();
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLocation == null)
        {
            startLocationUpdates();
        }
        if (mLocation != null)
        {
            latitude = mLocation.getLatitude();
            longitude = mLocation.getLongitude();
        }
        else
        {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i)
    {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
        {
            mGoogleApiClient.connect();
        }
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        if (mGoogleApiClient.isConnected())
        {
            mGoogleApiClient.disconnect();
        }
    }

    protected void startLocationUpdates()
    {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }

    @Override
    public void onLocationChanged(@NonNull Location location)
    {
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    }
    private boolean checkLocation()
    {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }
    private void showAlert()
    {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt)
                    {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt)
                    {}
                });
        dialog.show();
    }
    private boolean isLocationEnabled()
    {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}
