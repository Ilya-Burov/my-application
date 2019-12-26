package com.burov.userbase.interfaces;
//интерфейс dataHolder
import com.burov.userbase.pojo.User;

public interface DataHolder {
    User getUser(int index);
    void addUser(User user);
    void deleteUser(int index);
    void refreshUser(User userNew);
    int getSize();
}
