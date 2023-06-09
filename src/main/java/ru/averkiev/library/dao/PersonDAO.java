package ru.averkiev.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.averkiev.library.model.Book;
import ru.averkiev.library.model.Person;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person",
                new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public Optional<Person> show(String fullName) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE fullName=?", new Object[]{fullName},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(fullName, yearOfBirthday) VALUES (?, ?)",
                person.getFullName(), person.getYearOfBirthday());
    }

    public void update(int id, Person updatePerson) {
        jdbcTemplate.update("UPDATE Person SET fullName=?, yearOfBirthday=? WHERE id=?",
                updatePerson.getFullName(), updatePerson.getYearOfBirthday(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public List<Book> getBooks(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE Book.person_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }

}