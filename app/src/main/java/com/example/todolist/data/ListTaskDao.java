package com.example.todolist.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todolist.model.ListTask;

import java.util.List;

// Описание CRUD интерфейса модели ListTask
@Dao
public interface ListTaskDao {
    @Query("SELECT * FROM ListTask")
    LiveData<List<ListTask>> getAllLiveDataList();

    @Query("SELECT * FROM ListTask WHERE uid = :uid LIMIT 1")
    ListTask findByIdList(int uid);

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Если при вставке id объектов совпадут то будет произведена замена
    void insertList(ListTask listTask);

    @Update
    void updateList(ListTask listTask);

    @Delete
    void deleteList(ListTask listTask);

}
