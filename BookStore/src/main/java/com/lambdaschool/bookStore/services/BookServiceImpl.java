package com.lambdaschool.bookStore.services;

import com.lambdaschool.bookStore.exceptions.ResourceNotFoundException;
import com.lambdaschool.bookStore.models.Book;
import com.lambdaschool.bookStore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "BookService")
public class BookServiceImpl implements BookService
{
    @Autowired
    private BookRepository bookrepo;

    @Override
    public ArrayList<Book> findAll(Pageable pageable)
    {
        ArrayList<Book> bookList = new ArrayList<>();
        bookrepo.findAll().iterator().forEachRemaining(bookList::add);
        return bookList;
    }

    @Override
    public List<Book> findByBooktitle(String name)
    {
        List<Book> list = new ArrayList<>();
        bookrepo.findByBooktitleContainingIgnoreCase(name).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Book save(Book book)
    {
        Book newBook = new Book();
        newBook.setBooktitle(book.getBooktitle());
        newBook.setIsbn(book.getIsbn());
        newBook.setCopy(book.getCopy());

        return bookrepo.save(newBook);
    }

    @Override
    public Book update(Book book, long id)
    {
        Book currentBook = bookrepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));
        if (book.getBooktitle() != null)
        {
            currentBook.setBooktitle(book.getBooktitle());
        }

        if (book.getIsbn() != null)
        {
            currentBook.setIsbn(book.getIsbn());
        }

        if (book.getCopy() != currentBook.getCopy())
        {
            currentBook.setCopy(book.getCopy());
        }

        return bookrepo.save(currentBook);
    }

    @Override
    public void delete(long id) throws ResourceNotFoundException
    {

        if (bookrepo.findById(id).isPresent())
        {
            bookrepo.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException(Long.toString(id));
        }

    }

    @Transactional
    @Override
    public void saveBookWithAuthor(long bookid, long authorid)
    {
        bookrepo.insertInToAuthorBooks(bookid, authorid);
    }

    @Override
    public Book findBookById(long id) throws ResourceNotFoundException
    {
        return bookrepo.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));
    }
}
