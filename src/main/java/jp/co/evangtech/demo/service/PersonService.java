package jp.co.evangtech.demo.service;

import jp.co.evangtech.demo.entity.Person;
import jp.co.evangtech.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PersonService {

    @Autowired
    PersonRepository repository;

    public List<Person> findAll() {
        return repository.findAll(new Sort(Sort.Direction.ASC, "id"));
    }
}