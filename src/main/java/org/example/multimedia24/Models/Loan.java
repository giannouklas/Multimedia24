package org.example.multimedia24.Models;

import org.example.multimedia24.CurrentUserInfo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Loan implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Member member;
    private Book bookLoaned;
    private final LocalDate dateOfLoan;
    private final LocalDate dateOfReturn;
    private boolean hasExpired;

    private static List<Loan> LoanList = Collections.synchronizedList(new ArrayList<>());
    public static final int MAX_LOANS = 2;

    private Loan(Member m, Book b){
        this.member = m;
        this.bookLoaned = b;
        this.dateOfLoan = LocalDate.now();
        this.dateOfReturn = dateOfLoan.plusDays(5);
        boolean hasExpired = false;
    }
    protected static String checkCreateAdd(Member m, Book b, int pendingLoans){
        if (pendingLoans >= MAX_LOANS){ //user already has 2 Loans
            return "You have already 2 pending Loans";
        }
        else if(b.getNoOfCopies()<=0){ //no available copies
            return "There are no available copies at the time";
        }
        Loan newLoan = new Loan(m,b); //create Loan
        Book.updateNumberOfAvailableCopies(b.getISBN(),false); //one less available copy
        CurrentUserInfo.addToMyLoans(newLoan);
        LoanList.add(newLoan);
        return null;
    }

    public Member getMember(){return member;}
    public String getBorrowerUsername(){return member.getUsername();}
    public String getBorrowerEmail(){return member.getEmail();}
    public Book getBookLoaned(){return bookLoaned;}
    public String getBookTitle(){return bookLoaned.getTitle();}
    public String getBookAuthor(){return bookLoaned.getAuthor();}
    public double getAverageRating(){return bookLoaned.getAverageRating();}
    public String getBookISBN(){return bookLoaned.getISBN();}
    public String getDateOfLoan(){return dateOfLoan.toString();}
    public String getDateOfReturn(){return dateOfReturn.toString();}
    public boolean getHasExpired(){return hasExpired;}
    public String getLoanStatus(){
        if(hasExpired) return "Expired";
        else{
            LocalDate today = LocalDate.now();
            long daysRemaining = ChronoUnit.DAYS.between(today, this.dateOfReturn);
            if(daysRemaining==1)
                return daysRemaining+" day remaining";
            return daysRemaining+" days remaining";
        }
    }
    public static List<Loan> getLoanList() {
        return LoanList;
    }
    public static void setLoanList(List<Loan> l) {LoanList = l;}
    public void setMember(Member m){this.member = m;}
    public void setBookLoaned(Book b){this.bookLoaned = b;}

    public void checkAndUpdateLoanStatus() {
        LocalDate today = LocalDate.now();
        this.hasExpired = today.isAfter(this.dateOfReturn);// hasExpired is true if today is after dateOfReturn.
    }

    public String toString(){
        if(getHasExpired()){
            return "Member "+this.member.getUsername()+" has borrowed book "+this.bookLoaned.getTitle()+" and must return it because it has expired";
        }
        return "Member "+this.member.getUsername()+" has borrowed book "+this.bookLoaned.getTitle()+" and there are "+this.getLoanStatus();
    }
    public static String removeLoan(String username, String isbn, String date) {
        for (Loan l : LoanList) {
            if (l.getBookISBN().equals(isbn) && l.getMember().getUsername().equals(username) && l.getDateOfLoan().equals(date)) {
                Book.updateNumberOfAvailableCopies(isbn,true);
                LoanList.remove(l);
                String m = CurrentUserInfo.removeFromMyLoans(l);
                if (m==null)
                    return "Book with ISBN "+isbn+" has been returned by "+username+" .";
                else
                    return m;
            }
        }
        return "Loan not found.";
    }

    protected static String removeAllLoansForSpecificBook(String isbn) {
            List<Loan> loansToRemove = new ArrayList<>();
            for (Loan l : LoanList) {
                if (l.getBookLoaned().getISBN().equals(isbn)) {
                    loansToRemove.add(l);
                }
            }
            if (loansToRemove.isEmpty()) {
                return "No loans found for a book with ISBN " + isbn + ".";
            }
            // Remove all collected loans and update the number of available copies for each
            for (Loan loan : loansToRemove) {
                LoanList.remove(loan);
                Book.updateNumberOfAvailableCopies(loan.getBookISBN(), true);
            }

            return "All loans for book with ISBN " + isbn + " were successfully removed.";
    }

    protected static String removeAllLoansForSpecificMember(String username) {
        List<Loan> loansToRemove = new ArrayList<>();
        for (Loan l : LoanList) {
            if (l.getMember().getUsername().equals(username)) {
                loansToRemove.add(l);
            }
        }
        if (loansToRemove.isEmpty()) {
            return "No loans found for member with username " + username + ".";
        }
        for (Loan loan : loansToRemove) {
            LoanList.remove(loan);
            Book.updateNumberOfAvailableCopies(loan.getBookISBN(), true);
        }
        return "All loans for member with username " + username + " were successfully removed.";
    }

}
