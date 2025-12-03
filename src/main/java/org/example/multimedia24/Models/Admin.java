package org.example.multimedia24.Models;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Admin extends User {
    @Serial
    private static final long serialVersionUID = 1L; // Ensure version compatibility

    private static List<Admin> AdminList = Collections.synchronizedList(new ArrayList<>());

    public Admin(String username, String password) {
        super(username, password);
    }

    public static String checkCreateAdd(String username, String password) {
        String m = "Some fields are invalid";
        if (username == null || username.trim().length() < 2) {return m;}
        if (password == null || password.trim().length() < 2) { return m;}
        for (Admin a: AdminList){
            if(a.getUsername().equals(username))
                return "Username already exists";
        }
        for (Member mem : Member.getMemberList()) {
            if (mem.getUsername().equals(username))
                return "Username already exists";
        }
        Admin newAdmin = new Admin(username, password);
        AdminList.add(newAdmin);
        return null; //If we get here it means that the admin was created successfully
    }

    public static Admin searchByUsernameAndPassword(String username, String password) {
        for(Admin a: AdminList){
            if(a.getUsername().equals(username) && a.getPassword().equals(password))
                return a;
        }
        return null;
    }

    public static List<Admin> getAdminList() {
        if(AdminList == null)
            AdminList = new ArrayList<>();
        return AdminList;
    }
    public static void setAdminList(List<Admin> l){AdminList = l;}
}

