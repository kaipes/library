package com.example.project.controller;

import com.example.project.dao.PeopleDAO;
import com.example.project.model.Book;
import com.example.project.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private PeopleDAO peopleDAO;

    @Autowired
    public PeopleController(PeopleDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    @GetMapping()
    public String index(Model model) {
        List<Person> people = peopleDAO.index();
        model.addAttribute("people", people);
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("person") Person person) {
        return "/people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") Person person) {
        peopleDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, @ModelAttribute("person") Person person, Model model) {
        model.addAttribute("Book", peopleDAO.show(id));
        return "/people/edit";
    }

    @PostMapping("/{id}")
    public String edit(@ModelAttribute("person") Person upPerson, @PathVariable("id") int id) {
        peopleDAO.update(id, upPerson);
        return "redirect:/people/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleDAO.delete(id);
        return "redirect:/people";
    }
}
