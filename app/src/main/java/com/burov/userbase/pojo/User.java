package com.burov.userbase.pojo;

import java.io.Serializable;
import java.util.Objects;
//создаем поля юзера, геттеры и сеттеры
public class User implements Serializable {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String country;
    private String city;
    private double balance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    // проверяем на соответствие объекты
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname)&&
                Objects.equals(phone, user.phone) &&
                Objects.equals(email, user.email)&&
                Objects.equals(country, user.country) &&
                Objects.equals(city, user.city) &&
                balance == user.balance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
