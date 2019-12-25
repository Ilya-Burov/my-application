package com.burov.userbase.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.burov.userbase.R;
// ������������ ����
public class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView name;
    private TextView email;

    public MyViewHolder(@NonNull View itemView) {
        // �������� � ���������� 
        super(itemView);
        //������� name � email �� view
        name = itemView.findViewById(R.id.textView_name);
        email = itemView.findViewById(R.id.textView_email);
    }
    // ��������� �����
    public void setName(String nameStr) {
        this.name.setText(nameStr);
    }
    // ��������� email
    public void setEmail(String emailStr) {
        this.email.setText(emailStr);
    }
}
