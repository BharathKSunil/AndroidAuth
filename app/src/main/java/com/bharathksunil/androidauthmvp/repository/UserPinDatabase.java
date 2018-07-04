package com.bharathksunil.androidauthmvp.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomDatabase;

import com.bharathksunil.androidauthmvp.model.UserPin;

@Database(entities = {UserPin.class}, version = 1, exportSchema = false)
public abstract class UserPinDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    @Dao
    public interface UserDao {
        /*@Query("SELECT * FROM UserPin WHERE uid = (:userIds)")
        UserPin getUsersPin(String userIds);*/

        @Query("SELECT * FROM UserPin")
        UserPin getUsersPin();

        @Insert
        void insertUserPin(UserPin... users);

        @Delete
        void removeUserPin(UserPin user);
    }
}
