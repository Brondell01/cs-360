package com.zybooks.module5application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class CreateAccount extends AppCompatActivity {
    private EditText userNameAccess;
    private EditText userPasswordAccess;
    private Button createButton;
    private List<Users> users;
    private Users mUser = new Users();
    private boolean nameSet;
    private boolean passwordSet;
    public UserDao userDao;

    //instantiate DB call to interact with USER database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        nameSet = false;
        passwordSet = false;

        userNameAccess = (EditText) findViewById(R.id.userNameAccess);
        userPasswordAccess = (EditText) findViewById(R.id.userPasswordAccess);
        userPasswordAccess.setEnabled(false);
        createButton = (Button) findViewById(R.id.createAcctButton);
        createButton.setEnabled(false);



        //dummy data
        //Users userTest = new Users("J","Brondell");
        //userDao.insertUser(userTest);

        UserDatabase userDatabase = Room.databaseBuilder(getApplicationContext(), UserDatabase.class,"users2.db").
                allowMainThreadQueries().build();
        userDao = userDatabase.userDao();


        //determines if Username has been entered, validates whether or not userName already exists, then
        // sets password field to editable.
        userNameAccess.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                users = userDao.getUserName(s.toString());
                if(users.size() == 0){
                   userPasswordAccess.setEnabled(true);
                }
                nameSet = true;
            }
        });

        userPasswordAccess.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().trim().isEmpty()){
                    createButton.setEnabled(true);

                }
                passwordSet = true;
            }
        });

    }

    public String buttonAddUser(View view){
        if(nameSet && passwordSet){
            mUser.setUserName(userNameAccess.getText().toString());
            mUser.setUserPassword(userPasswordAccess.getText().toString());

            users = userDao.getUserName(mUser.getUserName());
            if(users.size() == 0) {
                userDao.insertUser(mUser);

                Intent intent = new Intent(this, LogIn.class);
                startActivity(intent);
            }
        }

        return null;
    }
}