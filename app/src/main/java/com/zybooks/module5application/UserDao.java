package com.zybooks.module5application;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM Users WHERE userName = :username")
    public List<Users> getUserName(String username);

    @Query("SELECT * FROM Users WHERE userName = :username")
    public Users getUserByName(String username);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    public void insertUser(Users user);

    @Update
    public void updateUser(Users user);

    @Delete
    public void deleteUser(Users user);



}
