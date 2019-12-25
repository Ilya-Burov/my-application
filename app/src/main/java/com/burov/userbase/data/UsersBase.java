package com.burov.userbase.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.burov.userbase.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class UsersBase {
    //создаем список юзеров
    private List<User> users = new ArrayList<>();
    // Открываем базу данных
    public static UsersBase loadFromDataBase(Context context) {
        UsersBase usersBase = new UsersBase();

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
      
        Cursor cursor = database.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);

        int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
        int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
        int surnameIndex = cursor.getColumnIndex(DBHelper.KEY_SURNAME);
        int emailIndex = cursor.getColumnIndex(DBHelper.KEY_EMAIL);
        int phoneIndex = cursor.getColumnIndex(DBHelper.KEY_PHONE);
        int countryIndex = cursor.getColumnIndex(DBHelper.KEY_COUNTRY);
        int cityIndex = cursor.getColumnIndex(DBHelper.KEY_CITY);
        int balanceIndex = cursor.getColumnIndex(DBHelper.KEY_BALANCE);
        // Пробегаемся по базе данных и устанавливаем поля юзера
        while (cursor.moveToNext()) {
            User user = new User();

            user.setId(cursor.getInt(idIndex));
            user.setName(cursor.getString(nameIndex));
            user.setSurname(cursor.getString(surnameIndex));
            user.setEmail(cursor.getString(emailIndex));
            user.setPhone(cursor.getString(phoneIndex));
            user.setCountry(cursor.getString(countryIndex));
            user.setCity(cursor.getString(cityIndex));
            user.setBalance(Double.parseDouble(cursor.getString(balanceIndex)));
            // Добавляем юзера в список
            usersBase.addUser(user);
        }

        cursor.close();
        database.close();

        return usersBase;
    }
    // Добавление юзера
    public void addUser(User user) {
        users.add(user);
    }
    // Удаление юзера по индексу
    public void removeUser(int index) {
        users.remove(index);
    }
    // Удаление юзера по объекту
    public void removeUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            User tmp = users.get(i);
            if (tmp.getId() == user.getId())
                users.remove(i);
        }
    }

    public User getUser(int index) {
        return users.get(index);
    }

    public User getUserByID(int id) {
        for (User user : users) {
            if (user.getId() == id)
                return user;
        }
        return null;
    }

    public int getSize() {
        return users.size();
    }
}
