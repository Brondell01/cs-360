package com.zybooks.module5application;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM Item")
    public List<Item> getItems();

    @Query("SELECT * FROM Item WHERE itemName= :itemName")
    public List<Item> getItemName(String itemName);

    @Query("SELECT * FROM Item WHERE itemName = :itemName")
    public Item getItemByName(String itemName);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    public void insertItem(Item item);

    @Update
    public void updateItem(Item item);

    @Delete
    public void deleteItem(Item item);

}
