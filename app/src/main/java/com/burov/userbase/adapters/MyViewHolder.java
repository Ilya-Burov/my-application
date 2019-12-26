package com.burov.userbase.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.burov.userbase.R;
// Обязательные поля
public class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView name;
    private TextView email;

    public MyViewHolder(@NonNull View itemView) {
        // передаем в суперкласс 
        super(itemView);
        //Находим name и email по view
        name = itemView.findViewById(R.id.textView_name);
        email = itemView.findViewById(R.id.textView_email);
    }
    // Установка имени
    public void setName(String nameStr) {
        this.name.setText(nameStr);
    }
    // Установка email
    public void setEmail(String emailStr) {
        this.email.setText(emailStr);
    }
}
