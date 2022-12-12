package com.example.demospringboot.rest;

import com.example.demospringboot.model.Person;
import com.example.demospringboot.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/person")
public class PersonRestController {
    private static final Logger logger = LoggerFactory.getLogger(PersonRestController.class);

    private final PersonService personService;

    @Autowired
    public PersonRestController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("{personId}")
    public ResponseEntity<Person> getPersonById(@PathVariable UUID personId) {
        logger.info("getPersonById");
        return new ResponseEntity<>(personService.getPersonById(personId), HttpStatus.OK);
    }

    @GetMapping("{login}")
    public ResponseEntity<Person> getPersonByLogin(@PathVariable String login) {
        logger.info("getPersonByLogin");
        return new ResponseEntity<>(personService.findByLogin(login), HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<List<Person>> getAllPersons() {
        logger.info("getAllPersons");
        return new ResponseEntity<>(personService.getAllPersons(), HttpStatus.OK);
    }
}
