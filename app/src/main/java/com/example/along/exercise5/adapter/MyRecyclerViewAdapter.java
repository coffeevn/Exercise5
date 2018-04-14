package com.example.along.exercise5.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.along.exercise5.R;
import com.example.along.exercise5.fragment.DeleteFragment;
import com.example.along.exercise5.fragment.EditOrDoneFragment;
import com.example.along.exercise5.model.Item;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class MyRecyclerViewAdapter extends RealmRecyclerViewAdapter<Item, MyRecyclerViewAdapter.MyViewHolder> {
    Context context;
    public MyRecyclerViewAdapter(OrderedRealmCollection<Item> data) {
        super(data, true);
        setHasStableIds(true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        context=parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Item obj = getItem(position);
        holder.data = obj;
        holder.toDo.setText(obj.getName());
        holder.dueTo.setText(obj.getDate());
        switch (obj.getPriority()) {
            case 1:
                holder.priority.setText("HIGH");
                holder.priority.setTextColor(Color.RED);
                break;
            case 3:
                holder.priority.setText("LOW");
                holder.priority.setTextColor(Color.GREEN);
                break;
            default:
                holder.priority.setText("NORMAL");
                holder.priority.setTextColor(Color.YELLOW);
                break;
        }

        holder.ll_Item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditOrDoneFragment editOrDoneFragment = new EditOrDoneFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id",obj.getId());
                bundle.putString("name",obj.getName());
                bundle.putString("date",obj.getDate());
                bundle.putInt("priority",obj.getPriority());
                editOrDoneFragment.setArguments(bundle);
                editOrDoneFragment.show(((AppCompatActivity)context).getFragmentManager(),"EditOrDoneDialog");
            }
        });
        holder.ll_Item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DeleteFragment deleteFragment = new DeleteFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id",obj.getId());
                deleteFragment.setArguments(bundle);
                deleteFragment.show(((AppCompatActivity)context).getFragmentManager(),"DeleteDialog");
                return false;
            }
        });
    }

    @Override
    public long getItemId(int index) {
        return getItem(index).getId();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView toDo;
        TextView dueTo;
        TextView priority;
        LinearLayout ll_Item;
        public Item data;

        MyViewHolder(View view) {
            super(view);
            toDo = view.findViewById(R.id.tv_Name);
            dueTo = view.findViewById(R.id.tv_DueTo);
            priority = view.findViewById(R.id.tv_Priority);
            ll_Item = view.findViewById(R.id.ll_Item);
        }
    }


}
