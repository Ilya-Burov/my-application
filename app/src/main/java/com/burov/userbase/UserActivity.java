package com.burov.userbase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.burov.userbase.pojo.User;

public class UserActivity extends AppCompatActivity {
    public static final String STATUS_KEY = "statusKey";
    public static final String STATUS_NEW = "new";
    public static final String STATUS_OLD = "old";
    public static final String USER_KEY = "userKey";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Передаем в суперкласс
        super.onCreate(savedInstanceState);
        // Устанавливаем отображаемый слой 
        setContentView(R.layout.activity_user);

        final Intent data = getIntent();

        fillViews(data);
         // Устанавливаем обработку нажатия
        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = findViewById(R.id.editText_name);
                EditText email = findViewById(R.id.editText_email);

                if (!name.getText().toString().isEmpty() && !email.getText().toString().isEmpty()) {
                    // Создаем юзера и намерение 
                    User user = new User();
                    Intent intent = new Intent();

                    if (data.getStringExtra(STATUS_KEY).equals(STATUS_OLD)) {
                        intent.putExtra(STATUS_KEY, STATUS_OLD);
                        user.setId(((User) data.getSerializableExtra(UserActivity.USER_KEY)).getId());
                    }
                    else if (data.getStringExtra(STATUS_KEY).equals(STATUS_NEW))
                        intent.putExtra(STATUS_KEY, STATUS_NEW);
// Устанавливаем значения полей
                    user.setName(name.getText().toString());
                    user.setSurname(((EditText)findViewById(R.id.editText_surname)).getText().toString());
                    user.setEmail(((EditText)findViewById(R.id.editText_email)).getText().toString());
                    user.setPhone(((EditText)findViewById(R.id.editText_phone)).getText().toString());
                    user.setCountry(((EditText)findViewById(R.id.editText_country)).getText().toString());
                    user.setCity(((EditText)findViewById(R.id.editText_city)).getText().toString());

                    if (!((EditText)findViewById(R.id.editText_balance)).getText().toString().isEmpty())
                        user.setBalance(Double.parseDouble(((EditText)findViewById(R.id.editText_balance)).getText().toString()));

                    intent.putExtra(UserActivity.USER_KEY, user);

                    setResult(RESULT_OK, intent);
                    finish();
                } else
                    Toast.makeText(getApplicationContext(), getString(R.string.warning), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fillViews(Intent data) {
  // Если статус keys совпадают создаем юзера
        if (data.getStringExtra(STATUS_KEY).equals(STATUS_OLD)) {
            User user = (User) data.getSerializableExtra(UserActivity.USER_KEY);
//Устанавливаем значения полей юзера отображаемых на activity 
            ((EditText)findViewById(R.id.editText_name)).setText(user.getName());
            ((EditText)findViewById(R.id.editText_surname)).setText(user.getSurname());
            ((EditText)findViewById(R.id.editText_email)).setText(user.getEmail());
            ((EditText)findViewById(R.id.editText_phone)).setText(user.getPhone());
            ((EditText)findViewById(R.id.editText_country)).setText(user.getCountry());
            ((EditText)findViewById(R.id.editText_city)).setText(user.getCity());
            ((EditText)findViewById(R.id.editText_balance)).setText(String.valueOf(user.getBalance()));
        }
    }
}
