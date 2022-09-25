package com.example.project.dao;

import com.example.project.model.Book;
import com.example.project.model.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/project";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "root";
    private static Connection connection;

    public BookDAO() {
    }

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Book> index() {
        Statement statement = null;
        List<Book> bookList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            String SQL = "SELECT * FROM book";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("book_id"));
                book.setName(resultSet.getString("name"));
                book.setAuthor(resultSet.getString("author"));
                book.setYear(resultSet.getInt("year"));
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public Book show(int id) {
        try {
            String SQL = "SELECT * FROM book JOIN person ON person.person_id = book.person_id WHERE book.book_id =?";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("book_id"));
                book.setName(resultSet.getString("name"));
                book.setAuthor(resultSet.getString("author"));
                book.setYear(resultSet.getInt("year"));
                Person person = new Person();
                person.setId(resultSet.getInt("person_id"));
                person.setFull_name(resultSet.getString("full_name"));
                person.setDate(resultSet.getInt("date"));
                book.setPerson(person);
                return book;
            } else {
                String SQL1 = "SELECT * FROM book WHERE book_id=?";
                PreparedStatement statement1 = connection.prepareStatement(SQL1);
                statement1.setInt(1, id);
                ResultSet resultSet1 = statement1.executeQuery();
                if (resultSet1.next()) {
                    Book book = new Book();
                    book.setId(id);
                    book.setName(resultSet1.getString("name"));
                    book.setAuthor(resultSet1.getString("author"));
                    book.setYear(resultSet1.getInt("year"));
                    book.setPerson(null);
                    return book;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(Book Book) {
        String SQL = "INSERT INTO book(name, author, year) VALUES(?, ?, ?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL);
            statement.setString(1, Book.getName());
            statement.setString(2, Book.getAuthor());
            statement.setInt(3, Book.getYear());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, Book Book) {
        String SQL = "UPDATE book SET name=?, author=?, year=? WHERE book_id=?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL);
            statement.setString(1, Book.getName());
            statement.setString(2, Book.getAuthor());
            statement.setInt(3, Book.getYear());
            statement.setInt(4, id);
            statement.executeUpdate();
            List<Book> people = index();
            System.out.println(people.size());
            for (int i = 0; i < people.size(); i++) {
                System.out.println(people.get(i).getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String SQL = "DELETE FROM book WHERE book_id=?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePerson(int id) {
        String SQL = "UPDATE book SET person_id = null WHERE book_id=?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void assign(int book_id, int person_id) {
        String SQL = "UPDATE book SET person_id=? WHERE book_id =?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL);
            statement.setInt(1, person_id);
            statement.setInt(2, book_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
