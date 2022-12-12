package com.example.demospringboot.model;


import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

public enum Role {
    ADMIN,
    DOCTOR,
    PATIENT;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> permissions = new HashSet<>();
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
