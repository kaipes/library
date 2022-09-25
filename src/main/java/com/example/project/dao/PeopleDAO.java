package com.example.project.dao;

import com.example.project.model.Book;
import com.example.project.model.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PeopleDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/project";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "root";
    private static Connection connection;

    public PeopleDAO() {
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

    public List<Person> index() {
        Statement statement = null;
        List<Person> people = new ArrayList<>();
        try {
            statement = connection.createStatement();
            String SQL = "SELECT * FROM person";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("person_id"));
                person.setFull_name(resultSet.getString("full_name"));
                person.setDate(resultSet.getInt("date"));
                people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        for (int i = 0; i < people.size(); i++) {
//            System.out.println(people.get(i).getFull_name());
//        }
        return people;
    }

    public Person show(int id) {
        try {
            String SQL = "SELECT * FROM person JOIN book ON person.person_id = book.person_id  WHERE person.person_id=?";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                List<Book> bookList = new ArrayList<>();
                System.out.println("NULL");
                Person person = new Person();
                person.setId(id);
                person.setFull_name(resultSet.getString("full_name"));
                person.setDate(resultSet.getInt("date"));
                do {
                    Book book = new Book();
                    book.setId(resultSet.getInt("book_id"));
                    book.setName(resultSet.getString("name"));
                    book.setAuthor(resultSet.getString("author"));
                    book.setYear(resultSet.getInt("year"));
                    bookList.add(book);
                } while (resultSet.next());
                person.setBook(bookList);
                if (bookList.size() == 0) {
                    System.out.println("ноль");
                }
                for (int i = 0; i< bookList.size(); i++) {
                    System.out.println(bookList.get(i));
                }
                return person;
            } else {
                String SQL1 = "SELECT * FROM person WHERE person.person_id=?";
                PreparedStatement statement1 = connection.prepareStatement(SQL1);
                statement1.setInt(1, id);
                ResultSet resultSet1 = statement1.executeQuery();
                if (resultSet1.next()) {
                    Person person = new Person();
                    person.setId(id);
                    person.setFull_name(resultSet1.getString("full_name"));
                    person.setDate(resultSet1.getInt("date"));
                    person.setBook(null);
                    return person;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(Person person) {
        String SQL = "INSERT INTO person(full_name, date) VALUES(?, ?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL);
            statement.setString(1, person.getFull_name());
            statement.setInt(2, person.getDate());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, Person person) {
        String SQL = "UPDATE person SET full_name=?, date=? WHERE person_id=?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL);
            statement.setString(1, person.getFull_name());
            statement.setInt(2, person.getDate());
            statement.setInt(3, id);
            statement.executeUpdate();
            List<Person> people = index();
//            System.out.println(people.size());
//            for (int i = 0; i < people.size(); i++) {
//                System.out.println(people.get(i).getFull_name());
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String SQL = "DELETE FROM person WHERE person_id=?";
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
}
