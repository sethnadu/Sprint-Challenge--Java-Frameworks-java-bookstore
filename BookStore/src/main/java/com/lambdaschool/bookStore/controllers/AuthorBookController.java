package com.lambdaschool.bookStore.controllers;

import com.lambdaschool.bookStore.models.Author;
import com.lambdaschool.bookStore.models.Book;
import com.lambdaschool.bookStore.models.ErrorDetail;
import com.lambdaschool.bookStore.services.AuthorService;
import com.lambdaschool.bookStore.services.BookService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.*;

@RestController
public class AuthorBookController
{
    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    // GET localhost:2019/authors
    @ApiOperation(value = "Return All Authors", response = Author.class, responseContainer = "List")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve(0..N)"),
                               @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Results of records per page"),
                               @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(asc|desc). Default sort order is ascending. Multiple sort criteria are supported.")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "All Authors Found", response = Author.class),
            @ApiResponse(code = 404, message = "Author's Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/authors", produces = {"application/json"})
    public ResponseEntity<?> listAllAuthors(@PageableDefault(page = 0, size = 3, sort = {"lastname"}, direction = Sort.Direction.ASC)
                                                              Pageable pageable)
    {
        List<Author> myAuthors = authorService.findAll(pageable);
        return new ResponseEntity<>(myAuthors, HttpStatus.OK);
    }

    // GET localhost:2019/books
    @ApiOperation(value = "Return All Books", response = Book.class, responseContainer = "List")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve(0..N)"),
                               @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Results of records per page"),
                               @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(asc|desc). Default sort order is ascending. Multiple sort criteria are supported.")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "All Books Found", response = Book.class),
            @ApiResponse(code = 404, message = "Book's Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/books", produces = {"application/json"})
    public ResponseEntity<?> listAllBooks(@PageableDefault(page = 0, size = 3, sort = {"booktitle"}, direction = Sort.Direction.ASC)
                                                  Pageable pageable)
    {
        ArrayList<Book> myBooks = bookService.findAll(pageable);
        return new ResponseEntity<>(myBooks, HttpStatus.OK);
    }

    //GET localhost:2019/books/{bookid}
    @ApiOperation(value = "Return Book by id", response = Book.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Book Found By ID", response = Book.class),
            @ApiResponse(code = 404, message = "Book Not Found By ID", response = ErrorDetail.class)})
    @GetMapping(value = "/books/{bookid}",
                produces = {"application/json"})
    public ResponseEntity<?> getBookById(
            @PathVariable
                    Long bookid)
    {
        Book b = bookService.findBookById(bookid);
        return new ResponseEntity<>(b, HttpStatus.OK);
    }

    //GET localhost:2019/authors/{authorid}
    @ApiOperation(value = "Return Author by id", response = Author.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Author Found By ID", response = Author.class),
            @ApiResponse(code = 404, message = "Author Not Found By ID", response = ErrorDetail.class)})
    @GetMapping(value = "/authors/{authorid}",
                produces = {"application/json"})
    public ResponseEntity<?> getAuthorById(
            @PathVariable
                    Long authorid)
    {
        Author a = authorService.findAuthorById(authorid);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    //GET localhost:2019/books/title/{booktitle}
    @ApiOperation(value = "Return Book by Name", response = Book.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Book Title Found", response = Book.class),
            @ApiResponse(code = 404, message = "Book's Title Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/books/title/{booktitle}",
                produces = {"application/json"})
    public ResponseEntity<?> getBookByTitleContaining(
            @PathVariable String booktitle)
    {
        List<Book> currentBook = bookService.findByBooktitle(booktitle);
        return new ResponseEntity<>(currentBook, HttpStatus.OK);
    }

    //GET localhost:2019/authors/lastname/{lastname}
    @ApiOperation(value = "Return Author by Last Name", response = Author.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Author's LastName Found", response = Author.class),
            @ApiResponse(code = 404, message = "Author's Last Name Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/authors/lastname/{lastname}",
                produces = {"application/json"})
    public ResponseEntity<?> getAuthorByLastNameContaining(
            @PathVariable String lastname)
    {
        List<Author> currentAuthor = authorService.findAuthorByLastname(lastname);
        return new ResponseEntity<>(currentAuthor, HttpStatus.OK);
    }


//    ###########################################==ADMIN/DATA ACCESS==#####################################

    //PUT localhost:2019/data/books/{bookid}
    @ApiOperation(value = "Update Book by Id", response = Book.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Book Updated", response = void.class),
            @ApiResponse(code = 500, message = "Book Not Updated", response = ErrorDetail.class)})
    @PutMapping(value = "/data/books/{bookid}")
    public ResponseEntity<?> updateBook(
            @RequestBody
                    Book updateBook,
            @PathVariable
                    long bookid)
    {
        bookService.update(updateBook, bookid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //POST localhost:2019/data/books/{bookid}/authors/{authorid}
    @ApiOperation(value = "Add Book with Author", response = Book.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Book Added to Author", response = void.class),
            @ApiResponse(code = 500, message = "Book Not Added to Author", response = ErrorDetail.class)})
    @PostMapping(value="/data/books/{bookid}/authors/{authorid}",consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addTodo(@Valid
                                     @RequestBody Book book, @PathVariable long bookid, @PathVariable long authorid){

        bookService.saveBookWithAuthor(bookid, authorid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //DELETE localhost:2019/data/books/{bookid}
    @ApiOperation(value = "Delete by Book Id", response = Book.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Book Deleted By Id", response = void.class),
            @ApiResponse(code = 500, message = "Book Not Deleted By Id", response = ErrorDetail.class)})
    @DeleteMapping("/data/books/{bookid}")
    public ResponseEntity<?> deleteBookById(
            @PathVariable
                    long bookid)
    {
        bookService.delete(bookid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
