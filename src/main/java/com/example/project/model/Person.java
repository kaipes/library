package com.example.project.model;

import java.util.List;

public class Person {
    private int id;
    private String full_name;
    private int date;
    private List<Book> book;
    public Person() {

    }
    public Person(int id, String full_name, int date, List<Book> book) {
        this.id = id;
        this.full_name = full_name;
        this.date = date;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public List<Book> getBook() {
        return book;
    }

    public void setBook(List<Book> book) {
        this.book = book;
    }

}
