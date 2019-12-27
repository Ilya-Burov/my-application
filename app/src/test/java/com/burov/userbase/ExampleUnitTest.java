package com.burov.userbase;

import org.junit.Test;

import static org.junit.Assert.*;
import com.burov.userbase.data.UsersBase;
import com.burov.userbase.pojo.User;

import java.util.ArrayList;
import java.util.List;


public class ExampleUnitTest {
    @Test
    public void add() {
        UsersBase newUsersBase= new UsersBase();
         List<User> users = new ArrayList<>();
         User user1 = new User();
         newUsersBase.addUser(user1);
         int expected =1;
         assertEquals(expected,newUsersBase.getSize() );
    }
    @Test
    public void remove(){
        UsersBase newUsersBase= new UsersBase();
        List<User> users = new ArrayList<>();
        User user1 = new User();
        newUsersBase.addUser(user1);
        newUsersBase.removeUser(user1);
        assertEquals(0, newUsersBase.getSize());
    }

    @Test
    public void addCountry(){
        User user2 = new User();
        user2.setCountry("Russia");
        String country = "Russia";
        assertEquals(country, user2.getCountry());

    }







}

