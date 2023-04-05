package com.example.to_do_list.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do_list.R;
import com.example.to_do_list.databinding.RecyclerItemBinding;
import com.example.to_do_list.model.List_Table;

import java.util.ArrayList;

public class List_Adapter extends RecyclerView.Adapter<List_Adapter.ViewHolder> {
 private ArrayList<List_Table> listtask;
 private Context context;

 private OnItemClickListener listener;


    public List_Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerItemBinding recyclerItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_item,
                parent,false);
        return new ViewHolder(recyclerItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
List_Table currenttask=listtask.get(position);
holder.recyclerItemBinding.setTasks(currenttask);

    }

    @Override
    public int getItemCount() {
        return listtask==null ? 0: listtask.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
private RecyclerItemBinding recyclerItemBinding;

     public ViewHolder(RecyclerItemBinding recyclerItemBinding) {
         super(recyclerItemBinding.getRoot());
         this.recyclerItemBinding=recyclerItemBinding;

         recyclerItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     int clickedPosition = getAdapterPosition();

                     if (listener!= null && clickedPosition != RecyclerView.NO_POSITION){
                         listener.onItemClick(listtask.get(clickedPosition));
                     }
                 }
         });
     }
 }
 public interface OnItemClickListener{
        void onItemClick(List_Table list_table);
 }

 public void setClickListener(OnItemClickListener listener){
        this.listener=listener;
 }

    public void setListtask(ArrayList<List_Table> newlisttask) {
//        this.listtask = listtask;
//        notifyDataSetChanged();
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff
                (new Task_Diff_call(listtask,newlisttask),false);

        listtask = newlisttask;
        result.dispatchUpdatesTo(List_Adapter.this);
    }

}
