package com.example.todolist.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.todolist.model.ListTask;
import com.example.todolist.model.Task;

@Database(entities = {ListTask.class, Task.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    // Даем доступ к Data Access Object
    public abstract ListTaskDao taskListDao();
    public abstract TaskDao taskDao();

}
