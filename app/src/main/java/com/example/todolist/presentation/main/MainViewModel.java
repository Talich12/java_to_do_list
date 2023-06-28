package com.example.todolist.presentation.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist.App;
import com.example.todolist.model.ListTask;
import com.example.todolist.model.Task;

import java.util.List;

public class MainViewModel extends ViewModel {

    // Получение данных из приложения
    private LiveData<List<Task>> taskLiveData = App.getInstance().getTaskDao().getAllLiveDataTask();

    private LiveData<List<ListTask>> listLiveData = App.getInstance().getTaskListDao().getAllLiveDataList();

    public LiveData<List<ListTask>> getListLiveData(){
        return listLiveData;
    }
    public LiveData<List<Task>> getTaskLiveData(){
        return taskLiveData;
    }
}
