package com.example.demospringboot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Person")
@Table(name = "person",
        uniqueConstraints = {
                @UniqueConstraint(name = "person_login_unique", columnNames = "login"),
        })
public class Person implements UserDetails {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "login", nullable = false, unique = true, columnDefinition = "VARCHAR(255)")
    private String login;
    @Column(name = "first_name", nullable = false, columnDefinition = "VARCHAR(255)")
    private String firstName;
    @Column(name = "last_name", nullable = false, columnDefinition = "VARCHAR(255)")
    private String lastName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
    @Column(name = "account_non_expired", columnDefinition = "BOOLEAN default TRUE", nullable = false)
    private boolean accountNonExpired;
    @Column(name = "account_non_locked", columnDefinition = "BOOLEAN default TRUE", nullable = false)
    private boolean accountNonLocked;
    @Column(name = "credentials_non_expired", columnDefinition = "BOOLEAN default TRUE", nullable = false)
    private boolean credentialsNonExpired;
    @Column(name = "enabled", columnDefinition = "BOOLEAN default TRUE", nullable = false)
    private boolean enabled;
    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(255)")
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole().getAuthorities();
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!id.equals(person.id)) return false;
        return login.equals(person.login);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + login.hashCode();
        return result;
    }
}
