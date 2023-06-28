package com.example.todolist.presentation.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.todolist.App;
import com.example.todolist.R;
import com.example.todolist.model.ListTask;
import com.example.todolist.presentation.face.taskListFaceActivity;

import java.util.List;

public class listTaskAdapter extends RecyclerView.Adapter<listTaskAdapter.ListTaskViewHolder> {

    private SortedList<ListTask> sortedList;

    public listTaskAdapter() {

        sortedList = new SortedList<>(ListTask.class, new SortedList.Callback<ListTask>() {
            @Override
            public int compare(ListTask o1, ListTask o2) {

                return (int) (o2.timestamp - o1.timestamp);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(ListTask oldItem, ListTask newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(ListTask item1, ListTask item2) {
                return item1.uid == item2.uid;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }


    @NonNull
    @Override
    public ListTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListTaskViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListTaskViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<ListTask> listTask) {
        sortedList.replaceAll(listTask);
    }

    static class ListTaskViewHolder extends RecyclerView.ViewHolder {

        TextView titleList;
        View delete;

        ListTask listTask;

        public ListTaskViewHolder(@NonNull final View itemView) {
            super(itemView);

            titleList = itemView.findViewById(R.id.list_title);

            delete = itemView.findViewById(R.id.list_delete);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    taskListFaceActivity.start((Activity) itemView.getContext(), listTask);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    App.getInstance().getTaskListDao().deleteList(listTask);
                }
            });

        }
        public void bind(ListTask listTask) {
            this.listTask = listTask;

            titleList.setText(listTask.title);
        }

    }


}

