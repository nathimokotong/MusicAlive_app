package com.example.android.musicalive;

/**
 * Created by Codetribe on 2016/11/16.
 */

public class User {
    private String name;
    private String surname;
    private String password;
    private String email;


    public void User() {
        name = this.name;
        surname = this.surname;
        password = this.password;
        email = this.email;
    }

    public void User(String n,String s,String p,String e)
    {
        name = n;
        surname = s;
        password = p;
        email = e;
    }


}
