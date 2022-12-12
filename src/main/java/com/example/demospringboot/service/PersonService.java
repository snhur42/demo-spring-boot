package com.example.demospringboot.service;

import com.example.demospringboot.model.Person;
import com.example.demospringboot.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Qualifier("personService")
@Transactional
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person getPersonById(UUID personId) {
        return personRepository.getReferenceById(personId);
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person findByLogin(String login) {
        return personRepository.findByLogin(login).orElseThrow();
    }
}
