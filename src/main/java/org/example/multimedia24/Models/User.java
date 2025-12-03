package org.example.multimedia24.Models;

import java.io.Serial;
import java.io.Serializable;

public abstract class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected String username;
    protected String password;

    //constructor
    protected User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {return username;}
    protected String getPassword(){return password;}
    public void setUsername(String username){this.username = username;}

}
