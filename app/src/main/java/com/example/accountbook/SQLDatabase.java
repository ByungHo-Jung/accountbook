package com.example.accountbook;

public class SQLDatabase {
    int year;
    int month;
    int day;
    int money;
    String name;
    String type;

    public SQLDatabase(int year, int month, int day, int money, String name){
        this.year = year;
        this.month = month;
        this.day = day;
        this.money = money;
        this.name = name;
        this.type = null;
    }

    public SQLDatabase(int year, int month, int day, int money, String name, String type){
        this.year = year;
        this.month = month;
        this.day = day;
        this.money = money;
        this.name = name;
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
