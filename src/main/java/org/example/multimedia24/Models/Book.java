package org.example.multimedia24.Models;

import java.io.Serial;
import java.io.Serializable;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.example.multimedia24.Models.Rating.removeAllRatingsForSpecificBook;

public class Book implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private int year;
    private int noOfCopies;
    private int numberOfRatings;
    private Category bookCategory;
    double avgRating;

    private static List<Book> BookList = Collections.synchronizedList(new ArrayList<>());

    //constructor
    public Book(String title, String author, String publisher, String isbn, int year, int noOfCopies, Category bookCategory) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.year = year;
        this.noOfCopies = noOfCopies;
        this.bookCategory = bookCategory;
        this.avgRating= 0;
        this.numberOfRatings = 0;
    }
    //Check the fields(title, author etc). If they are valid create an object and add it to the list
    public static String checkCreateAdd(String title, String author, String publisher, String isbn, String year, String noOfCopies, String category) {
        int numericYear;
        int numericNoOfCopies;
        try {
            numericYear = Integer.parseInt(year); // Try to parse the string as an integer
        } catch (NumberFormatException e) {
            return "Field 'Year' must be an integer";
        }
        try {
            numericNoOfCopies = Integer.parseInt(noOfCopies); // Try to parse the string as an integer
        } catch (NumberFormatException e) {
            return "Number of Copies must be an Integer";
        }

        Category categ = null;
        boolean categoryExists = false;
        if (title == null || title.trim().length() < 2) {return "Invalid title";}
        if (author == null || author.trim().length() < 2) {return "Invalid Author";}
        if (publisher == null || publisher.trim().length() < 2) {return "Invalid Publisher";}
        if (numericYear < 1000 || numericYear > Year.now().getValue()) {return "Invalid Year";} //year must not exceed current year
        if (isbn == null || isbn.trim().length() < 2) {return "Invalid ISBN";}
        for(Book b : BookList){
            if(b.getISBN().equals(isbn))
                return "ISBN already exists";
        }
        if (numericNoOfCopies<0)
            return "No. of copies can't be negative";
        for(Category c : Category.getCategoryList()){
            if(c.getName().equals(category)){
                categoryExists = true;
                categ = c;
                break;
            }
        }
        if(!categoryExists)
            return "Category does not exist";
        Book newBook = new Book(title,author,publisher,isbn,numericYear,numericNoOfCopies,categ);
        BookList.add(newBook);
        return null; //If we get here it means that the book was created successfully
    }

    public String edit(String newTitle, String newAuthor, String newPublisher, String newISBN, String newYear,
                       String newNoOfCopies, String newCategory){
        int numericYear;
        int numericNoOfCopies;
        String oldISBN = this.isbn;
        try {
            numericYear = Integer.parseInt(newYear); // Try to parse the string as an integer
        } catch (NumberFormatException e) {
            return "Field 'Year' must be an integer";
        }
        try {
            numericNoOfCopies = Integer.parseInt(newNoOfCopies); // Try to parse the string as an integer
        } catch (NumberFormatException e) {
            return "Number of Copies must be an Integer";
        }
        Category categ = null;
        boolean categoryExists = false;
        if (newTitle == null || newTitle.trim().length() < 2) {return "Invalid title";}
        if (newAuthor == null || newAuthor.trim().length() < 2) {return "Invalid Author";}
        if (newPublisher == null || newPublisher.trim().length() < 2) {return "Invalid Publisher";}
        if (numericYear < 1000 || numericYear > Year.now().getValue()) {return "Invalid Year";} //year must not exceed current year
        if (newISBN == null || newISBN.trim().length() < 2) {return "Invalid ISBN";}
        for(Book b : BookList){
            if(newISBN.equals(b.getISBN()) && !newISBN.equals(oldISBN)){
                return "ISBN already exists";
            }
        }
        if (numericNoOfCopies<0) {return "No. of copies can't be negative";}
        for(Category c : Category.getCategoryList()){
            if(c.getName().equals(newCategory)){
                categoryExists = true;
                categ = c;
            }
        }
        if(!categoryExists){
            return "Category does not exist";
        }
        setTitle(newTitle);
        setAuthor(newAuthor);
        setPublisher(newPublisher);
        setISBN(newISBN);
        setYear(numericYear);
        setNoOfCopies(numericNoOfCopies);
        setBookCategory(categ);
        for (Loan l: Loan.getLoanList()){
            if(l.getBookISBN().equals(oldISBN)){
                l.setBookLoaned(this);
            }
        }
        return null;
    }

    //setters&getters
    public String getTitle() {return title;}
    public String getAuthor() {return author;}
    public String getPublisher() {return publisher;}
    public int getYear() {return year;}
    public String getCategory() {return bookCategory.getName();}
    public String getISBN() {return isbn;}
    public int getNoOfCopies(){return noOfCopies;}
    public int getNumberOfRatings(){return numberOfRatings;}
    public double getAverageRating(){return Math.round(avgRating * 100.0) / 100.0;}
    public static List<Book> getBookList() {
        return BookList;
    }
    public static List<Book> getTop5Books() {
        List<Book> sortedBooks = new ArrayList<>(BookList);
        sortedBooks.sort((book1, book2) -> Double.compare(book2.getAverageRating(), book1.getAverageRating()));
        int size = Math.min(sortedBooks.size(), 5);
        return new ArrayList<>(sortedBooks.subList(0, size));
    }


    public void setTitle(String title) {this.title = title;}
    public void setAuthor(String author) {this.author = author;}
    public void setPublisher(String publisher) {this.publisher = publisher;}
    public void setISBN(String isbn) {this.isbn = isbn;}
    public void setYear(int year) {this.year = year;}
    public void setNoOfCopies(int noOfCopies){this.noOfCopies = noOfCopies;}
    public void setAverageRating(double num){this.avgRating = num;}
    public void setBookCategory(Category bookCategory){this.bookCategory = bookCategory;}
    public static void setBookList(List<Book> l) {BookList = l;}

    protected static void updateNumberOfAvailableCopies(String isbn, boolean BookIsReturned){
        for(Book b : BookList) {
            if(b.getISBN().equals(isbn)) {
                if (BookIsReturned)
                    b.noOfCopies++;
                else
                    b.noOfCopies--;
            }
        }
    }

    public String toString(){return "Title: "+title+", Author: "+author+", Rating: "+getAverageRating();}

    public static String removeBook(String isbn) {
        for (Book b : BookList) {
            if (b.getISBN().equals(isbn)) {
                String m1 = Loan.removeAllLoansForSpecificBook(isbn);
                String m2 = Comment.removeAllCommentsForSpecificBook(b);
                String m3 = removeAllRatingsForSpecificBook(b);
                BookList.remove(b);
                return m2+" "+m1+" "+m3+" Book with ISBN " + isbn + " was successfully removed.";
            }
        }
        return "No book found with ISBN " + isbn + ".";
    }

    protected static String removeAllBooksForSpecificCategory(String category) {
        List<Book> booksToRemove = new ArrayList<>();
        for (Book b : BookList) {
            if (b.getCategory().equals(category)) {
                booksToRemove.add(b);
            }
        }
        if (booksToRemove.isEmpty()) {
            return "No books found for a category " + category + ".";
        }
        for (Book b : booksToRemove) {
            Book.removeBook(b.getISBN());
        }
        return "All books for Category " + category + " were successfully removed.";
    }

    protected void updateAvgRating(int num, boolean removal){
        double sum = this.numberOfRatings*this.getAverageRating();
        if(removal && this.numberOfRatings<=0)
            return;
        else if(removal){ //if a rating is removed do this
            sum = sum - num;
            this.numberOfRatings--;
        }
        else{ //if a rating is being added do this
            sum = sum + num;
            this.numberOfRatings++;
        }

        if(this.numberOfRatings==0)
            setAverageRating(0);
        else
            setAverageRating(sum/this.numberOfRatings);
    }

}
