package com.lambdaschool.bookStore.services;

import com.lambdaschool.bookStore.models.Author;
import org.springframework.data.domain.Pageable;

import java.util.*;

public interface AuthorService
{
    ArrayList<Author> findAll(Pageable pageable);

    Author findAuthorById(long id);

    List<Author> findAuthorByLastname(String name);
}
