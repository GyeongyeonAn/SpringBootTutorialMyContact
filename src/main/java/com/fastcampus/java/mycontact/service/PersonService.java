package com.fastcampus.java.mycontact.service;

import com.fastcampus.java.mycontact.controller.dto.PersonDto;
import com.fastcampus.java.mycontact.domain.Person;
import com.fastcampus.java.mycontact.exception.PersonNotFoundException;
import com.fastcampus.java.mycontact.exception.RenameIsNotPermittedException;
import com.fastcampus.java.mycontact.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Page<Person> getAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    public List<Person> getPeopleByName(String name){
        return personRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Person getPerson(Long id){
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public void put(PersonDto personDto){
        Person person = new Person();
        person.set(personDto);
        person.setName(personDto.getName());
        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id, PersonDto personDto){
        Person person = personRepository.findById(id)
                .orElseThrow(PersonNotFoundException::new);

        if(!person.getName().equals(personDto.getName())){
            throw new RenameIsNotPermittedException();
        }

        person.set(personDto);

        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id, String name){
        Person person = personRepository.findById(id)
                .orElseThrow(PersonNotFoundException::new);

        person.setName(name);

        personRepository.save(person);
    }

    @Transactional
    public void delete(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(PersonNotFoundException::new);

        person.setDeleted(true);

        personRepository.save(person);
    }
}
