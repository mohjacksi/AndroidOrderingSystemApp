package com.mjacksi.novapizza.RoomDatabase;

import androidx.lifecycle.LiveData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface FoodDao {
    @Insert
    void insert(FoodRoom food);

    @Update
    void update(FoodRoom food);

    @Delete
    void delete(FoodRoom food);

    @Query("DELETE FROM food_table")
    void deleteAllFoods();

    @Query("SELECT * FROM food_table ORDER BY title")
    LiveData<List<FoodRoom>> getAllFoods();

    @Query("SELECT * FROM food_table WHERE count > 0 ORDER BY title")
    LiveData<List<FoodRoom>> getAllOrderedFoods();

    @Query("UPDATE food_table SET count = 0")
    void resetAllOrders();

    @Query("SELECT SUM(count) FROM food_table")
    LiveData<Integer> getCount();

}
