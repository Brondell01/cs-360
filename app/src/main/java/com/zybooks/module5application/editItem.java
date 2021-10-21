package com.zybooks.module5application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class editItem extends AppCompatActivity {
    private TextView editText;
    private EditText editName;

    private EditText editCount;
    private Button updateButton;
    private Button cancelButton;
    Item itemEdit,item = new Item();
    public ItemDao itemDao;
    private boolean itemExist, smsText;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        itemExist = false;

        editText = (TextView) findViewById(R.id.editTextView);
        editName = (EditText) findViewById(R.id.editItemName);
        editCount = (EditText) findViewById(R.id.editItemCount);
        editCount.setVisibility(View.INVISIBLE);
        updateButton = (Button) findViewById(R.id.editButton);
        cancelButton = (Button) findViewById(R.id.cancelEditButton);

        ItemDatabase itemDatabase = Room.databaseBuilder(getApplicationContext(), ItemDatabase.class,"items2.db").
                allowMainThreadQueries().build();
        itemDao = itemDatabase.itemDao();

       SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        smsText = sharedPref.getBoolean("MMS", false);


        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                item =itemDao.getItemByName(s.toString().toLowerCase(Locale.ROOT).trim());
                if(null != item){
                    itemExist = true;
                    editCount.setVisibility(View.VISIBLE);

                }

            }
        });


    }


    public void update(View view){
        if(itemExist) {
            itemEdit = item;
            try{
            int count = Integer.parseInt((editCount.getText().toString()));
            itemEdit.setItemCount(count);
            itemDao.updateItem(itemEdit);

                //send SMS text to user if they opted in and if inventory change is low
                if(count <= 10 && smsText){
                    SmsManager smgr = SmsManager.getDefault();
                    String message = "INVENTORY APP: low inventory alert for " + item.getItemName() ;
                    smgr.sendTextMessage("15555215554",null,message, null,null);
                }

                Intent intent = new Intent(this, DisplayActivity.class);
                startActivity(intent);
            } catch (NumberFormatException e){
                editText.setText("Enter a Valid Item Count to Edit.");
                editText.setTextSize(20);
                editText.setTextColor(Color.rgb(200,0,0));
            }

        }else{
            editText.setText("Enter a Valid Item Name to Edit.");
            editText.setTextSize(20);
            editText.setTextColor(Color.rgb(200,0,0));

        }
    }

    public void cancelEdit(View view){
        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }


}