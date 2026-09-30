package model;

import java.io.Serializable;

public abstract class User implements Serializable {
    private String userId, password, name, email, contactNo;

    public User(String userId, String password, String name, String email, String contactNo) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.contactNo = contactNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String v) {
        userId = v;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String v) {
        password = v;
    }

    public String getName() {
        return name;
    }

    public void setName(String v) {
        name = v;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String v) {
        email = v;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String v) {
        contactNo = v;
    }

    public abstract String getRole();
}
