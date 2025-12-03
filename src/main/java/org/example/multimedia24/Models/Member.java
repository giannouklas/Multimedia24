package org.example.multimedia24.Models;

import org.example.multimedia24.CurrentUserInfo;
import org.example.multimedia24.Utilities;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Member extends User {
    @Serial
    private static final long serialVersionUID = 1L;

    private String email;
    private String id;
    private String name;
    private String surname;

    private static List<Member> MemberList = Collections.synchronizedList(new ArrayList<>());
    
    public Member( String username, String password, String email, String id, String name, String surname) {
        super(username, password);
        this.email = email;
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public static String checkCreateAdd(String username, String name,String surname, String email, String password, String id) {
        String m = "No field can have \nless than 2 characters";
        if (username == null || username.trim().length() < 2) {return m;}
        if (name == null || name.trim().length() < 2) return m;
        if (surname == null || surname.trim().length() < 2) {return m;}
        if (email == null || email.trim().length() < 2) {return m;}
        if (password == null || password.trim().length() < 2) {return m;}
        if (id == null || id.trim().length() < 2) {return m;}
        if (!id.matches("\\d+")){
            return "ID can only contain digits 0-9";
        }

        for (Member mem : MemberList) {
            if (mem.getUsername().equals(username))
                return "Username already exists";
            if(mem.getId().equals(id))
                return "ID already exists";
            if (mem.getEmail().equals(email))
                return "Email already exists";
        }
        for (Admin a: Admin.getAdminList()){
            if(a.getUsername().equals(username))
                return "Username already exists";
        }
        Member newMember = new Member(username, password, email, id, name, surname);
        MemberList.add(newMember);
        return null; //If we get here it means that the member was created successfully
    }

    public String edit(String newName,String newSurname, String newUsername, String newEmail, String newID){
        String m = "No field can have \nless than 2 characters";
        String oldUsername = getUsername();
        String oldEmail = getEmail();
        String oldID = getId();
        if (newUsername == null || newUsername.trim().length() < 2) {return m;}
        if (newName == null || newName.trim().length() < 2) return m;
        if (newSurname == null || newSurname.trim().length() < 2) {return m;}
        if (newEmail == null || newEmail.trim().length() < 2) {return m;}
        if (newID == null || newID.trim().length() < 2) {return m;}

        for (Member mem : MemberList) {
            if (newUsername.equals(mem.getUsername()) && !newUsername.equals(oldUsername))
                return "Username already exists";
            if(newID.equals(mem.getId()) && !newID.equals(oldID))
                return "ID already exists";
            if (newEmail.equals(mem.getEmail()) && !newEmail.equals(oldEmail))
                return "Email already exists";
        }
        for (Admin a: Admin.getAdminList()){
            if(a.getUsername().equals(newUsername)){
                return "Admin with username '" + newUsername + "' already exists.";
            }
        }
        setName(newName);
        setSurname(newSurname);
        setUsername(newUsername);
        setEmail(newEmail);
        setId(newID);
        for (Loan l: Loan.getLoanList()){
            if(l.getBorrowerUsername().equals(oldUsername)){
                l.setMember(this);
            }
        }
        return null; //If we get here it means that the member was created successfully
    }

    public static Member searchByUsernameAndPassword(String username, String password) {
        for(Member m: MemberList){
            if(m.getUsername().equals(username) && m.getPassword().equals(password)){
                return m;
            }
        }
        return null;
    }

    public void borrowBook(Book b) {
        String Message = Loan.checkCreateAdd(this, b, CurrentUserInfo.getMyLoans().size());
        if (Message==null){
            Utilities.showInfoAlert("Loan Completed","You have successfully borrowed: "+b.getTitle(), "You can View your pending Loans in your profile tab");
        }
        else{
            Utilities.showInfoAlert("Oops", "You can't borrow this book", Message);
        }
    }

    public static String removeMember(String username) {
        for (Member m : MemberList) {
            if (m.getUsername().equals(username)) {
                String m1 = Loan.removeAllLoansForSpecificMember(username);
                String m2 = Comment.removeAllCommentsForSpecificMember(m);
                String m3 = Rating.removeAllRatingsForSpecificMember(m);
                MemberList.remove(m);
                return m1+". "+m2+".\n"+m3+". Member " + username + " was successfully removed.";
            }
        }
        return "No user found with username '" + username + "'.";
    }

    //setters & getters
    public String getEmail(){return email;}
    public String getName(){return name;}
    public String getId() {return id;}
    public String getSurname(){return surname;}
    public static List<Member> getMemberList() {
        if(MemberList == null)
            MemberList = new ArrayList<>();
        return MemberList;
    }
    public static void setMemberList(List<Member> l){
        MemberList = l;
    }
    public void setId(String id) {this.id = id;}
    public void setName(String name){this.name = name;}
    public void setSurname(String surname){this.surname = surname;}
    public void setEmail(String email){this.email = email;}

    //password should be removed at some point
    public String toString(){return "Name: "+name+", Surname: "+surname+", Username: "+username+", ID: "+id+
            ", Credentials: "+email+" "+password;}
}
