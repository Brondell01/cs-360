package com.zybooks.module5application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class addItem extends AppCompatActivity {
    private TextView addHeader;
    private EditText addName;
    private EditText addCount;
    private Button addItem;
    Item itemAdd,item = new Item();
    public ItemDao itemDao;
    public String name;
    public int count;
    private boolean itemExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemExist = true;

        addHeader = (TextView) findViewById(R.id.addHeader);
        addName = (EditText) findViewById(R.id.itemNameAdd);
        addCount = (EditText) findViewById(R.id.itemCountAdd);
        addItem = (Button) findViewById(R.id.addItemButton);
        addItem.setEnabled(false);

        ItemDatabase itemDatabase = Room.databaseBuilder(getApplicationContext(), ItemDatabase.class,"items2.db").
                allowMainThreadQueries().build();
        itemDao = itemDatabase.itemDao();

        addName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            //checks if item already exists and wont allow an additional item to be created.
            public void afterTextChanged(Editable s) {
                item =itemDao.getItemByName(s.toString().trim());
                if(null == item){
                    itemExist = false;
                    addItem.setEnabled(true);
                }else{
                    addItem.setEnabled(false);

                }

            }
        });
    }


    public void addItem(View view) {
        try {
            int countAdd = Integer.parseInt((addCount.getText().toString()));
            itemAdd = createItem(addName.getText().toString().toLowerCase(Locale.ROOT), countAdd);
            itemDao.insertItem(itemAdd);

            Intent intent = new Intent(this, DisplayActivity.class);
            startActivity(intent);
        } catch (NumberFormatException e){
            addHeader.setText("Enter a Valid Item Count to Edit.");
            addHeader.setTextSize(20);
            addHeader.setTextColor(Color.rgb(200,0,0));
        }

    }
    public void cancelAdd(View view){
        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }

    public Item createItem(String name, int count){
        Item item = new Item();
        item.setItemName(name);
        item.setItemCount(count);
        return item;
    }


}