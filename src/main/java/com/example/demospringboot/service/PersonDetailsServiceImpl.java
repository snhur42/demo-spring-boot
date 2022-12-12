package com.example.demospringboot.service;

import com.example.demospringboot.model.Person;
import com.example.demospringboot.repository.PersonRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@Qualifier("personDetailsServiceImpl")
public class PersonDetailsServiceImpl implements UserDetailsService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonDetailsServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person loadUserByUsername(String login) {
        if (personRepository.existsByLogin(login)) {
            return personRepository.findByLogin(login).orElseThrow();
        } else{
            throw new EntityExistsException("There is no AppUser with this login: " + login);
        }
    }

    public Person loadUserById(UUID personId) {
        if (personRepository.existsById(personId)) {
            return personRepository.findById(personId).orElseThrow();
        } else {
            throw new EntityExistsException("There is not User with this id: " + personId);
        }
    }

}

