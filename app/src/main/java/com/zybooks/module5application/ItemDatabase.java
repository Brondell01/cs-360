package com.zybooks.module5application;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities ={Item.class},version = 1)
public abstract class ItemDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "items2.db";

    private static ItemDatabase itemDatabase;

    public abstract ItemDao itemDao();


}
