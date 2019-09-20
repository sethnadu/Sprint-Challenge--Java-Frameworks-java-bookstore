package com.lambdaschool.bookStore.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.*;

@ApiModel(value = "Authors", description = "Author Entity")
@Entity
@Table(name = "author")
public class Author
{
    @ApiModelProperty(name = "authorid", value = "primary key for Author", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long authorid;

    @ApiModelProperty(name = "lastname", value = "Author's Last Name", required = true, example = "Butcher")
    private String lastname;

    @ApiModelProperty(name = "firstname", value = "Author's First Name", required = true, example = "Jim")
    private String firstname;

        @ApiModelProperty(name = "booksauthors", value = "Author(s) of Book", required = true, example = "Some Name, Some Name...")
        @ManyToMany(mappedBy = "author")
        @JsonIgnoreProperties("author")
        private List<Book> book = new ArrayList<>();

    public Author()
    {
    }

    public Author(String lastname, String firstname)
    {
        this.lastname = lastname;
        this.firstname = firstname;

    }

    public Author(String lastname, String firstname, ArrayList<Book> book)
    {
        this.lastname = lastname;
        this.firstname = firstname;
        this.book = book;
    }

    public long getAuthorid()
    {
        return authorid;
    }

    public void setAuthorid(long authorid)
    {
        this.authorid = authorid;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public List<Book> getBook()
    {
        return book;
    }

    public void setBook(List<Book> book)
    {
        this.book = book;
    }
}
