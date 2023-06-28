package com.example.todolist.presentation.adapter;

import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.todolist.App;
import com.example.todolist.R;
import com.example.todolist.model.Task;
import com.example.todolist.presentation.face.taskFaceActivity;

import java.util.List;

public class taskAdapter extends RecyclerView.Adapter<taskAdapter.TaskViewHolder> {

    private SortedList<Task> sortedList;

    public taskAdapter() {

        sortedList = new SortedList<>(Task.class, new SortedList.Callback<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if (!o2.important && o1.important){
                    return -1;
                }
                if (o2.important && !o1.important){
                    return 1;
                }
                if (!o2.done && o1.done) {
                    return 1;
                }
                if (o2.done && !o1.done) {
                    return -1;
                }
                return (int) (o2.timestamp - o1.timestamp);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Task oldItem, Task newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Task item1, Task item2) {
                return item1.uid == item2.uid;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bindDone(sortedList.get(position));
        holder.bindImpo(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<Task> tasks) {
        sortedList.replaceAll(tasks);
    }
    static class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView taskText;
        CheckBox done;
        View delete;

        CheckBox important;

        Task task;

        // переменная для "тихого обновления"
        boolean middleUpdate;

        public TaskViewHolder(@NonNull final View itemView) {
            super(itemView);

            taskText = itemView.findViewById(R.id.task_text);
            done = itemView.findViewById(R.id.done);
            delete = itemView.findViewById(R.id.delete);
            important = itemView.findViewById(R.id.important);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    taskFaceActivity.start((Activity) itemView.getContext(), task, task.listId);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    App.getInstance().getTaskDao().deleteTask(task);
                }
            });

            important.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean choose) {
                    if(!middleUpdate){
                        task.important = choose;
                        App.getInstance().getTaskDao().updateTask(task);
                    }
                }
            });

            done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (!middleUpdate) {
                        task.done = checked;
                        App.getInstance().getTaskDao().updateTask(task);
                    }
                    crossOutTask();
                }
            });
        }

        public void bindDone(Task task) {
            this.task = task;

            taskText.setText(task.text);
            crossOutTask();

            middleUpdate = true;
            done.setChecked(task.done);
            middleUpdate = false;
        }

        // Зачеркивание заметки если она выполнена
        private void crossOutTask(){
            if(task.done){
                taskText.setPaintFlags(taskText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                taskText.setPaintFlags(taskText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);

            }
        }
        public void bindImpo(Task task) {
            this.task = task;


            middleUpdate = true;
            important.setChecked(task.important);
            middleUpdate = false;

        }
    }
}
