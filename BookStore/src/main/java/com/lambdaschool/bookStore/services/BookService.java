package com.lambdaschool.bookStore.services;

import com.lambdaschool.bookStore.models.Book;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface BookService
{
    ArrayList<Book> findAll(Pageable pageable);

    Book save(Book book);

    List<Book> findByBooktitle(String name);

    Book findBookById(long id);

    Book update(Book book, long id);

    void delete(long id);

    void saveBookWithAuthor(long bookid, long authorid);
}
