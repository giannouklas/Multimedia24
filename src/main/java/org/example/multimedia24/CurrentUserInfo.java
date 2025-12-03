package org.example.multimedia24;

import org.example.multimedia24.Models.Admin;
import org.example.multimedia24.Models.Loan;
import org.example.multimedia24.Models.Member;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CurrentUserInfo {
    private static Member currentMember;
    private static Admin currentAdmin;
    private static List<Loan> myLoans = Collections.synchronizedList(new ArrayList<>());

    //setters & getters
    public static void setCurrentAdmin(Admin a) {currentAdmin = a;}
    public static void setCurrentMember(Member m) {
        currentMember = m;
        if(currentMember!=null){
            for (Loan l: Loan.getLoanList()){
                if (l.getMember().getUsername().equals(currentMember.getUsername())){
                    myLoans.add(l);
                }
            }}
    }
    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }
    public static Member getCurrentMember() {return currentMember;}
    public static List<Loan> getMyLoans() {
        return myLoans;
    }
    public static void addToMyLoans(Loan l){
        myLoans.add(l);
    }
    public static void clearMyLoans() {
        if (myLoans == null) {
            myLoans = new ArrayList<>(); // Reinitialize the list if it's null
        } else {
            myLoans.clear(); // Clear the list if it's already initialized
        }
    }
    public static String removeFromMyLoans(Loan loan) {
            for (Loan l : CurrentUserInfo.getMyLoans()) {
                if (loan == l) {
                    CurrentUserInfo.myLoans.remove(l);
                    return "You have returned this book";
                }
            }
            return null;
    }
}
