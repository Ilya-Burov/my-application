package com.burov.userbase;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.burov.userbase.adapters.MyClickRecyclerAdapter;
import com.burov.userbase.callback.MySwipeCallback;
import com.burov.userbase.data.DBHelper;
import com.burov.userbase.data.UsersBase;
import com.burov.userbase.interfaces.DataHolder;
import com.burov.userbase.interfaces.MyClickListener;
import com.burov.userbase.pojo.User;

public class MainActivity extends AppCompatActivity implements DataHolder, MyClickListener {
    private UsersBase usersBase;
    private MyClickRecyclerAdapter recViewAdapter;

    @Override
    // ������� ����� ��� �������� activity 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ������������� ���� ������������
        setContentView(R.layout.activity_main);

        usersBase = UsersBase.loadFromDataBase(getApplicationContext());
        // ������� ������ ���� Recycle View
        RecyclerView recViewUsers = findViewById(R.id.recView_users);
        recViewAdapter = new MyClickRecyclerAdapter(this, getLayoutInflater(), this);
        recViewUsers.setAdapter(recViewAdapter);
        recViewUsers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recViewUsers.setHasFixedSize(true);
  // �����
        MySwipeCallback swipeCallback = new MySwipeCallback(recViewAdapter);
        ItemTouchHelper itemTouch = new ItemTouchHelper(swipeCallback);
        itemTouch.attachToRecyclerView(recViewUsers);
// ���������� ������� ������, ������������ �� User Activity
        findViewById(R.id.fab_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUserActivity();
            }
        });
    }
    // �������� ����� �� �������
    @Override
    public User getUser(int index) {
        return usersBase.getUser(index);
    }

    @Override
// �������� ����� �� �������

    public void addUser(User user) {
        addToDataBase(user);
        usersBase.addUser(user);
        recViewAdapter.notifyDataSetChanged();
    }

    @Override
// �������  ����� �� �������

    public void deleteUser(int index) {
        deleteFromDataBase(index);
        usersBase.removeUser(usersBase.getUserByID(index));
        recViewAdapter.notifyDataSetChanged();
    }

    @Override
// ��������� �����

    public void refreshUser(User userNew) {
        User userOld = usersBase.getUserByID(userNew.getId());

        if (userOld != null) {
            userOld.setName(userNew.getName());
            userOld.setSurname(userNew.getSurname());
            userOld.setEmail(userNew.getEmail());
            userOld.setPhone(userNew.getPhone());
            userOld.setCountry(userNew.getCountry());
            userOld.setCity(userNew.getCity());
            userOld.setBalance(userNew.getBalance());

            recViewAdapter.notifyDataSetChanged();
            updateDatabase(userOld);
        }
    }

    @Override
// ��������� �������
    public int getSize() {
        return usersBase.getSize();
    }
// ���������� � ���� ������ ����� 
    private void addToDataBase(User user) {
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        // ��������� ���� ���������� � ������� ������� get
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_NAME, user.getName());
        contentValues.put(DBHelper.KEY_SURNAME, user.getSurname());
        contentValues.put(DBHelper.KEY_EMAIL, user.getEmail());
        contentValues.put(DBHelper.KEY_PHONE, user.getPhone());
        contentValues.put(DBHelper.KEY_COUNTRY, user.getCountry());
        contentValues.put(DBHelper.KEY_CITY, user.getCity());
        contentValues.put(DBHelper.KEY_BALANCE, user.getBalance());

        database.insert(DBHelper.TABLE_USERS, null, contentValues);

        Cursor cursor = database.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);
        int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
        cursor.moveToLast();
        user.setId(cursor.getInt(idIndex));

        cursor.close();
        database.close();
    }
   // �������� �� ���� ������ �� �������

    private void deleteFromDataBase(int index) {
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        database.delete(DBHelper.TABLE_USERS, DBHelper.KEY_ID + " = " + index, null);

        database.close();
    }
    // ���������� ���� ������

    private void updateDatabase(User user) {
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_NAME, user.getName());
        contentValues.put(DBHelper.KEY_SURNAME, user.getSurname());
        contentValues.put(DBHelper.KEY_EMAIL, user.getEmail());
        contentValues.put(DBHelper.KEY_PHONE, user.getPhone());
        contentValues.put(DBHelper.KEY_COUNTRY, user.getCountry());
        contentValues.put(DBHelper.KEY_CITY, user.getCity());
        contentValues.put(DBHelper.KEY_BALANCE, user.getBalance());

        database.update(DBHelper.TABLE_USERS, contentValues, DBHelper.KEY_ID + " = ?", new String[] {String.valueOf(user.getId())});

        database.close();
    }

    @Override
    public void onItemClick(View view, int index) {
        startUserActivity(index);
    }
  // ������ ���� �������� ��� �������� ������ �����
    private void startUserActivity() {
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra(UserActivity.STATUS_KEY, UserActivity.STATUS_NEW);
        startActivityForResult(intent, 0);
    }
// ������ ���� �������� ��� �������������� �������
    private void startUserActivity(int id) {
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra(UserActivity.STATUS_KEY, UserActivity.STATUS_OLD);
        intent.putExtra(UserActivity.USER_KEY, usersBase.getUserByID(id));
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data == null)
            return;

        if (resultCode == RESULT_OK) {
            User user = (User) data.getSerializableExtra(UserActivity.USER_KEY);
            if (user != null && data.getStringExtra(UserActivity.STATUS_KEY).equals(UserActivity.STATUS_NEW))
                this.addUser(user);
            else if (user != null && data.getStringExtra(UserActivity.STATUS_KEY).equals(UserActivity.STATUS_OLD))
                this.refreshUser(user);
        }
    }
}
