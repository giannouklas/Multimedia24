package org.example.multimedia24.Models;

import java.io.Serial;
import java.io.Serializable;

public abstract class Feedback implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected Member member;
    protected Book book;

    public Feedback(Member m, Book b) {
        this.member = m;
        this.book = b;
    }
    public Member getMember(){return this.member;}
    public Book getBook(){return this.book;}
    public String getBookTitle(){return this.book.getTitle();}
    public String getBookISBN(){return this.book.getISBN();}
    public String getUsername(){return this.member.getUsername();}
    public void setMember(Member member) {this.member = member;}
    public void setBookLoaned(Book book){this.book = book;}
}
