package com.lambdaschool.bookStore.services;

import com.lambdaschool.bookStore.models.Author;
import com.lambdaschool.bookStore.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "authorService")
public class AuthorServiceImpl implements AuthorService
{
    @Autowired
    private AuthorRepository authorrepo;

    @Override
    public ArrayList<Author> findAll(Pageable pageable)
    {
        ArrayList<Author> authorList = new ArrayList<>();
        authorrepo.findAll(pageable).iterator().forEachRemaining(authorList::add);
        return authorList;
    }

    @Override
    public Author findAuthorById(long id) throws EntityNotFoundException
    {
        return authorrepo.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
    }

    @Override
    public List<Author> findAuthorByLastname(String name)
    {
        List<Author> list = new ArrayList<>();
        authorrepo.findByLastnameContainingIgnoreCase(name).iterator().forEachRemaining(list::add);
        return list;
    }
}
