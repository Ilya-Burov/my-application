package com.burov.userbase.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.burov.userbase.R;
import com.burov.userbase.interfaces.DataHolder;
import com.burov.userbase.interfaces.ItemTouchHelperAdapter;
import com.burov.userbase.interfaces.MyClickListener;

import java.lang.ref.WeakReference;

public class MyClickRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener, ItemTouchHelperAdapter {
    private WeakReference<DataHolder> dataHolder;
    private final WeakReference<LayoutInflater> mInflater;
    private final MyClickListener myClickListener;

    public MyClickRecyclerAdapter(DataHolder dataHolder, LayoutInflater mInflater, MyClickListener myClickListener) {
        // Создаем слабые ссылки(Weak reference)
        this.dataHolder = new WeakReference<>(dataHolder);
        this.mInflater = new WeakReference<>(mInflater);
        this.myClickListener = myClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = mInflater.get();
        if (inflater != null) {
            MyViewHolder myViewHolder = new MyViewHolder(inflater.inflate(R.layout.item_user, viewGroup, false));
            myViewHolder.itemView.setOnClickListener(this);
            return myViewHolder;
        }
        else {
            throw new RuntimeException("It looks like activity is dead");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        if (dataHolder != null) {
            DataHolder l = dataHolder.get();
            if (l != null) {
                holder.setName(l.getUser(i).getName());
                holder.setEmail(l.getUser(i).getEmail());
                holder.itemView.setTag(l.getUser(i).getId());
            }
        }
    }

    @Override
    public int getItemCount() {
        if (dataHolder != null) {
            DataHolder l = dataHolder.get();
            if (l != null) {
                return l.getSize();
            }
        }
        return 0;
    }

    @Override
    // Обработка нажатий
    public void onClick(View v) {
        Integer position = (Integer)v.getTag();
        myClickListener.onItemClick(v, position);
    }

    @Override
    // Удаление юзера
    public void onItemDismiss(int index) {
        if (dataHolder != null) {
            DataHolder l = dataHolder.get();
            if (l != null) {
                l.deleteUser(index);
            }
        }
    }
}
