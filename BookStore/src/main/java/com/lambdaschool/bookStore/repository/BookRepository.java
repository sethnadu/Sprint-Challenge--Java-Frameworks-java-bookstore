package com.lambdaschool.bookStore.repository;

import com.lambdaschool.bookStore.models.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookRepository extends PagingAndSortingRepository<Book, Long>
{
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO wrote(bookid, authorid) values (:bookid, :authorid)", nativeQuery = true)
    void insertInToAuthorBooks(long bookid, long authorid);

    List<Book> findByBooktitleContainingIgnoreCase(String name);
}
