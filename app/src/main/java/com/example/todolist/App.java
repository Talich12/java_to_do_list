package com.example.todolist;

import android.app.Application;

import androidx.room.Room;

import com.example.todolist.data.AppDatabase;
import com.example.todolist.data.ListTaskDao;
import com.example.todolist.data.TaskDao;

public class App extends Application {

    private AppDatabase database;
    private TaskDao taskDao;

    private ListTaskDao taskListDao;

    private  static App instance;

    // Фунция для получения доступа к приложению (паттерн SingleTone)
    public static App getInstance(){
        return instance;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        instance = this;

        // Создание базы даных
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "TaskList_DB_33")
                .allowMainThreadQueries()
                .build();

        // Инициализация обектов классов наших моделей из базы данных
        taskDao = database.taskDao();
        taskListDao = database.taskListDao();


    }

    public AppDatabase getDatabase() {
        return database;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    public ListTaskDao getTaskListDao() {
        return taskListDao;
    }
    public void setTaskListDao(ListTaskDao taskListDao) {
        this.taskListDao = taskListDao;
    }
    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }
}

