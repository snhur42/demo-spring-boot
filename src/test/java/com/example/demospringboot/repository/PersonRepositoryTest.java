package com.example.demospringboot.repository;

import com.example.demospringboot.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@Sql({"/db/query/init_db.sql"})
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Value("${person.id}")
    private UUID personId;


    @Test
    void findUserDTOById() {
        Optional<Person> person = personRepository.findById(personId);
        assert (person.isPresent());
    }

    @Test
    void findUserDTOByIdWrongId() {
        Optional<Person> person = personRepository.findById(UUID.randomUUID());
        assert (person.isEmpty());
    }
}