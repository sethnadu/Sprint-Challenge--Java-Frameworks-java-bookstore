package com.lambdaschool.bookStore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "Book", description = "Book Entity")
@Entity
@Table(name = "book")
public class Book
{
    @ApiModelProperty(name = "bookid", value = "primary key for Book", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bookid;

    @ApiModelProperty(name = "booktitle", value = "Book's Title", required = true, example = "Some Book")
    private String booktitle;

    @ApiModelProperty(name = "isbn", value = "Book's ISBN", required = true, example = "92748391027")
    private String isbn;

    @ApiModelProperty(name = "copy", value = "Book's Copyright Date", required = true, example = "2019")
    private Integer copy;

    @ApiModelProperty(name = "authorbooks", value = "Author(s) of Book", required = true, example = "Some Name, Some Name,...")
    @ManyToMany
    @JoinTable(name = "wrote",
               joinColumns = {@JoinColumn(name = "bookid")},
               inverseJoinColumns = {@JoinColumn(name = "authorid")})
    @JsonIgnoreProperties("book")
    private List<Author> author = new ArrayList<>();

    public Book()
    {
    }

    public Book(String booktitle, String isbn)
    {
        this.booktitle = booktitle;
        this.isbn = isbn;
    }

    public Book(String booktitle, String isbn, Integer copy)
    {
        this.booktitle = booktitle;
        this.isbn = isbn;
        this.copy = copy;
    }

    public long getBookid()
    {
        return bookid;
    }

    public void setBookid(long bookid)
    {
        this.bookid = bookid;
    }

    public String getBooktitle()
    {
        return booktitle;
    }

    public void setBooktitle(String booktitle)
    {
        this.booktitle = booktitle;
    }

    public String getIsbn()
    {
        return isbn;
    }

    public void setIsbn(String isbn)
    {
        this.isbn = isbn;
    }

    public Integer getCopy()
    {
        return copy;
    }

    public void setCopy(Integer copy)
    {
        this.copy = copy;
    }

    public List<Author> getAuthor()
    {
        return author;
    }

    public void setAuthor(List<Author> author)
    {
        this.author = author;
    }
}
