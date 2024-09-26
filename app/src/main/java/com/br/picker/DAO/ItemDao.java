package com.br.picker.DAO;




import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.br.picker.model.Item;

import java.util.List;
@Dao
public interface ItemDao {
    @Insert
    long insert (Item item);
    @Delete
    int delete(Item item);
    @Update
    int update(Item item);

    @Query("SELECT * FROM item WHERE id = :id")
    Item queryForId(long id);

    @Query("SELECT * FROM item ORDER BY plaqueta ASC")
    List<Item> queryAllAscending();

    @Query("SELECT * FROM item ORDER BY plaqueta DESC")
    List<Item> queryAlLDownward();
}
