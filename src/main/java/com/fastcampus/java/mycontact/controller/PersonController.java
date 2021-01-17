package com.fastcampus.java.mycontact.controller;

import com.fastcampus.java.mycontact.controller.dto.PersonDto;
import com.fastcampus.java.mycontact.domain.Person;
import com.fastcampus.java.mycontact.exception.PersonNotFoundException;
import com.fastcampus.java.mycontact.exception.RenameIsNotPermittedException;
import com.fastcampus.java.mycontact.exception.dto.ErrorResponse;
import com.fastcampus.java.mycontact.repository.PersonRepository;
import com.fastcampus.java.mycontact.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/person")
@RestController
@Slf4j
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id){
        return personService.getPerson(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postPerson(@RequestBody PersonDto personDto){
        personService.put(personDto);
    }

    @PutMapping("/{id}")
    public void modifyPerson(@PathVariable Long id, @RequestBody PersonDto personDto){
        try {
            personService.modify(id, personDto);

        }catch (RuntimeException ex){
            log.error(ex.getMessage(),ex);
        }
    }

    @PatchMapping("/{id}")
    public void modifyPerson(@PathVariable Long id, String name){
        personService.modify(id, name);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id){
        personService.delete(id);
    }

}
