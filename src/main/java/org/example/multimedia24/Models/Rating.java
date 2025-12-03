package org.example.multimedia24.Models;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Rating extends Feedback{
    @Serial
    private static final long serialVersionUID = 1L;

    private int rate;

    private static List<Rating> RatingList = Collections.synchronizedList(new ArrayList<>());

    public Rating(Member m, Book b, int r) {
        super(m, b);
        this.rate = r;
    }

    public static String checkCreateAddOrUpdate(Member m, Book b, int num){
        boolean memberExists = false, bookExists=false;
        for(Rating rating : RatingList){
            if(rating.getUsername().equals(m.getUsername()) && rating.getBookISBN().equals(b.getISBN())){
                //if there is already a Rating by member m for book b then update it
                int oldRate = rating.getRate();
                rating.setRate(num);
                b.updateAvgRating(oldRate, true); //remove old rating
                b.updateAvgRating(num, false); //add new rating
                return "Rating updated";
            }
        }
        //else check m and b and then create a new Rating to add to the list
        for (Member member : Member.getMemberList()){
            if (m.getUsername().equals(member.getUsername())){
                memberExists = true;
                for (Book book : Book.getBookList()){
                    if (b.getISBN().equals(book.getISBN())) {
                        bookExists = true;
                        break;
                    }
                }
            }
        }
        if(!bookExists){
            return "The book you are trying to rate does not exist";
        }
        if(!memberExists){
            return "Error. Your Member info is invalid. Please report this to the administrator.";
        }
        Rating newRating = new Rating(m,b,num);
        int ratingToBeAdded = newRating.getRate();
        RatingList.add(newRating);
        b.updateAvgRating(ratingToBeAdded, false);
        return null; //New Rating created and added to the list
    }

    public static Rating findSpecificRating(Member m, String isbn){
        List<Rating> lr = getRatingsForSpecificBook(isbn);
        for (Rating r: lr){
            if(r.getUsername().equals(m.getUsername()))
                return r; //found rating r made by member m
        }
        return null; //rating not found
    }

    //setters & getters
    public int getRate(){return rate;}
    public static List<Rating> getRatingList() {return RatingList;}
    public static List<Rating> getRatingsForSpecificBook(String isbn) {
        List<Rating> ratingsForBook = new ArrayList<>();
        for (Rating r : RatingList) {
            if (r.getBookISBN().equals(isbn)) {
                ratingsForBook.add(r);
            }
        }
        return ratingsForBook;
    }
    public void setRate(int r){rate =r;}
    public static void setRatingList(List<Rating> l){RatingList = l;}


    public String toString(){return "User "+this.getUsername()+" rated "+this.getBookTitle()+" with "+rate+" stars.";}

    public static String removeRating(Member m, Book b) {
        for (Rating r : RatingList) {
            if (r.getBookISBN().equals(b.getISBN()) && r.getUsername().equals(m.getUsername())) {
                int ratingToBeRemoved = r.getRate();
                RatingList.remove(r);
                b.updateAvgRating(ratingToBeRemoved, true);
                return "The rating was deleted.";
            }
        }
        return "Rating not found";
    }

    public static String removeAllRatingsForSpecificBook(Book b) {
        List<Rating> ratingsToRemove = new ArrayList<>();
        for (Rating r : RatingList) {
            if (r.getBookISBN().equals(b.getISBN())) {
                ratingsToRemove.add(r);
            }
        }
        if (ratingsToRemove.isEmpty()) {
            return "No comments found for book " + b.getTitle() + ".";
        }
        for (Rating r : ratingsToRemove) {
            RatingList.remove(r);
        }
        return "All ratings for Book " + b.getTitle() + " were successfully removed.";
    }

    public static String removeAllRatingsForSpecificMember(Member m) {
        List<Rating> ratingsToRemove = new ArrayList<>();
        for (Rating r : RatingList) {
            if (r.getUsername().equals(m.getUsername())) {
                ratingsToRemove.add(r);
            }
        }
        if (ratingsToRemove.isEmpty()) {
            return "No ratings found from " + m.getUsername() + ".";
        }
        for (Rating r : ratingsToRemove) {
            RatingList.remove(r);
        }
        return "All ratings from Member " + m.getUsername() + " were successfully removed.";
    }

}
