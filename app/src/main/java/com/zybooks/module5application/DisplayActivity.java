package com.zybooks.module5application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {
    List<InventoryItem> item_list = new ArrayList<>();
private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        // Begin a new FragmentTransaction for adding a Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //** shared pref logic for creating a sharedPref external of activities/settings
        //SharedPreferences sharedPref = getSharedPreferences("myprefs",Context.MODE_PRIVATE);
        //String smsText = sharedPref.getString("MMS","no");

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean smsText = sharedPref.getBoolean("MMS", false);
        boolean notify = sharedPref.getBoolean("notificationSeen", false);

        //if("no"==smsText) {
        if(!notify){
            //displays the alert fragment for SMS access permissions.
            PermissionFragment dialog = new PermissionFragment();
            dialog.show(fragmentManager, "warningDialog");
        }

        FragmentList fragment = new FragmentList();

                Bundle args = new Bundle();
                args.putSerializable("valuesArray", (Serializable) item_list);
                fragment.setArguments(args);
                fragmentManager.beginTransaction().add(R.id.fragment_list, fragment).commit();


        //fragmentTransaction.add(R.id.fragment_list, fragment);
        //fragmentTransaction.commit();
    }

    public void addButton(View view){
        Intent intent = new Intent(this, addItem.class);
        startActivity(intent);
       // Intent intent = new Intent(this, AddFragment.class);
       // startActivity(intent);
    }

    public void editButton(View view){
        Intent intent = new Intent(this, editItem.class);
        startActivity(intent);
        // Intent intent = new Intent(this, AddFragment.class);
        // startActivity(intent);
    }


    //methods provide access to settings
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}