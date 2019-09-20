package com.lambdaschool.bookStore.repository;

import com.lambdaschool.bookStore.models.Author;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AuthorRepository extends PagingAndSortingRepository<Author, Long>
{

    List<Author> findByLastnameContainingIgnoreCase(String name);

}
