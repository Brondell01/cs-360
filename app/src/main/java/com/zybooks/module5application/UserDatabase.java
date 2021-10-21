package com.zybooks.module5application;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities={Users.class},version =1)
public abstract class UserDatabase extends RoomDatabase{
    private static final String DATABASE_NAME = "users2.db";

    private static UserDatabase userDatabase;

    //Singleton
    /*
    public static UserDatabase getInstance(Context context){
        if(userDatabase == null){
            userDatabase = Room.databaseBuilder(context, UserDatabase.class, DATABASE_NAME).
                    allowMainThreadQueries().build();
        }
        return userDatabase;
    }
*/
    public abstract UserDao userDao();

}