package com.example.demospringboot.service;

import com.example.demospringboot.model.Person;
import com.example.demospringboot.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
@ActiveProfiles("test-containers")
@SqlGroup({
        @Sql(value = "/db/query/init_db.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/query/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
})
public class PersonServiceTest {
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;

    @Value("${person.id}")
    private UUID personId;

    @Test
    void getAppUserById() {
        Person person_1 = personService.getPersonById(personId);
        Person person_2 = personRepository.findById(personId).get();
        assert (person_1.equals(person_2));
    }
}
