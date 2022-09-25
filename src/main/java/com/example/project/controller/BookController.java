package com.example.project.controller;

import com.example.project.dao.BookDAO;
import com.example.project.dao.PeopleDAO;
import com.example.project.model.Book;
import com.example.project.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private BookDAO bookDAO;
    private PeopleDAO peopleDAO;
    @Autowired
    public BookController(BookDAO bookDAO, PeopleDAO peopleDAO) {
        this.bookDAO = bookDAO;
        this.peopleDAO = peopleDAO;
    }

    @GetMapping()
    public String index(Model model) {
        List<Book> bookList = bookDAO.index();
        model.addAttribute("bookList", bookList);
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.show(id));
        System.out.println(peopleDAO.index().toString());
        model.addAttribute("ex", peopleDAO.index());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") Book book) {
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, @ModelAttribute("book") Book book, Model model) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PostMapping("/{id}")
    public String edit(@ModelAttribute("book") Book upBook, @PathVariable("id") int id) {
        bookDAO.update(id, upBook);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/release")
    public String deletePerson(@PathVariable("id") int id) {
        bookDAO.deletePerson(id);
        return "redirect:/books/{id}";
    }

//    @GetMapping("/{id}/assign")
//    public String assignPeople(@PathVariable("id") int id, Model model) {
////        List<Person> people = peopleDAO.index();
////        model.addAttribute("people", people);
//        //bookDAO.assign(id,);
//        return "redirect:/books/{id}";
//    }

    @PostMapping("{id}/assign")
    public String assignPerson(@PathVariable("id") int book_id, @ModelAttribute("person") Person person) {
        System.out.println(person.getId());
        bookDAO.assign(book_id, person.getId());
        return "redirect:/books/{id}";
    }
}
