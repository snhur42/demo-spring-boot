package com.example.demospringboot.repository;

import com.example.demospringboot.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    boolean existsByLogin(String login);

    Optional<Person> findByLogin(String login);
}
