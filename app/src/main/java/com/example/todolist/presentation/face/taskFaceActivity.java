package com.example.todolist.presentation.face;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.App;
import com.example.todolist.R;
import com.example.todolist.model.Task;

public class taskFaceActivity  extends AppCompatActivity {
    // Передаем заметку для манипуляций
    private static final String EXTRA_TASK = "taskFaceActivity.EXTRA_TASK";

    private Task task;

    private EditText editText;

    // Функция запуска
    public static void start(Activity caller, Task task, int listId){
        // интент для запуска одного activity из другого
        Intent intent = new Intent(caller, taskFaceActivity.class);
        intent.putExtra("listId", listId);
        if (task != null){
            intent.putExtra(EXTRA_TASK, task);
        }
        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_task_face);

        setTitle(R.string.task_face_title);

        editText = findViewById(R.id.text_for_task);

        // Если в интенте есть заметка мы вставляем ее текст если нет то создаем новую
        if (getIntent().hasExtra(EXTRA_TASK)){
            task = getIntent().getParcelableExtra(EXTRA_TASK);
            editText.setText(task.text);
        } else{
            task = new Task();
            task.listId = getIntent().getExtras().getInt("listId");
        }

    }
    // Фукнция для извелечения меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Сохранение заметки
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_save:
                if(editText.getText().length() > 0){
                    task.text = editText.getText().toString();
                    task.done = false;
                    task.important = false;
                    task.timestamp = System.currentTimeMillis();
                    // проверка новая ли заметка
                    if (getIntent().hasExtra(EXTRA_TASK)){
                        App.getInstance().getTaskDao().updateTask(task);
                    } else{
                        App.getInstance().getTaskDao().insertTask(task);
                    }
                    finish();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
