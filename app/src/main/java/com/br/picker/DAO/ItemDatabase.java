package com.br.picker.DAO;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.br.picker.model.Item;

@Database(entities = {Item.class},version = 1, exportSchema = false)
public abstract class ItemDatabase extends RoomDatabase {

    public abstract  ItemDao itemDao();

    private static ItemDatabase instance;

    public static ItemDatabase getDataBase(final Context context){

        if(instance == null){
            synchronized (ItemDatabase.class){
                if(instance==null){
                    instance = Room.databaseBuilder(context,ItemDatabase.class,"item.db").allowMainThreadQueries().build();
                }
            }
        }
        return instance;
    }
}
