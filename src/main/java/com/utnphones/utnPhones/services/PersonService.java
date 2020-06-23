package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.domain.Person;
import com.utnphones.utnPhones.exceptions.UserNotfoundException;
import com.utnphones.utnPhones.repository.PersonRepository;
import com.utnphones.utnPhones.utils.PasswordConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class PersonService {
    private PersonRepository personRepository;

    @Autowired
    public PersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person login(String username, String password) throws UserNotfoundException, NoSuchAlgorithmException {
        Person user = personRepository.getByUsername(username, PasswordConverter.generatePassword(password));
        return Optional.ofNullable(user).orElseThrow(UserNotfoundException::new);
    }
}
