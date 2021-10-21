package com.zybooks.module5application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogIn extends AppCompatActivity {
    public static final String EXTRA_USER_STATUS = "com.zybooks.project.USERNAME";
    private EditText userName;
    private EditText userPassword;
    private Button logInButton;
    private Button createAccount;
    private boolean validUser;
    private boolean nameEntered;
    private boolean passwordEntered;
    private boolean validName;
    public UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        validName = false;
        validUser = false;


        userName= (EditText) findViewById(R.id.userName);
        userPassword= (EditText) findViewById(R.id.userPassword);
        logInButton= (Button) findViewById(R.id.buttonLogIn);
        logInButton.setEnabled(false);
        createAccount = (Button) findViewById(R.id.createAccount);

        UserDatabase userDatabase = Room.databaseBuilder(getApplicationContext(), UserDatabase.class,"users2.db").
                allowMainThreadQueries().build();
        userDao = userDatabase.userDao();



        //determines if username as been entered.
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().isEmpty()) {
                    nameEntered = false;
                }else{
                    nameEntered= true;

                }

            }
        });

        //determines if UserPassword has been entered, validates whether or not login button is actionable
        userPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().isEmpty()) {
                    passwordEntered = false;
                }else{
                    passwordEntered= true;
                    if(nameEntered){
                        logInButton.setEnabled(true);
                    }
                }
            }
        });


    }

    public void userNameValidation(Users user, EditText userName){
        String storedUserName = user.getUserName();
        String enteredUserName = userName.getText().toString();

        if(storedUserName.equals(enteredUserName)){
            validName = true;
        }

    }

    public void passwordValidation(Users user, EditText password){
        String storedPassword = user.getUserPassword();
        String enteredPassword = password.getText().toString();

        if(storedPassword.equals(enteredPassword)) {
            validUser = true;
        }

    }


    public void buttonLogIn(View view) {
       Users user = userDao.getUserByName(userName.getText().toString());

    if(null!=user){
       userNameValidation(user, userName);
       if(validName) {
           passwordValidation(user, userPassword);
           if (validUser) {
               Intent intent = new Intent(this, DisplayActivity.class);
               intent.putExtra(EXTRA_USER_STATUS, validUser);
               startActivity(intent);
           }
       }
        } else{
            //TODO: handle failed login
            Intent intent = new Intent(this, CreateAccount.class);
            startActivity(intent);
        }
    }

    public void buttonCreateAccount(View view) {

        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }
}