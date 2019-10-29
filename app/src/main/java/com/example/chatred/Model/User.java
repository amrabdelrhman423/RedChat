package com.example.chatred.Model;

public class User {
    String id;
    String username;
    String imageUrl;
    String phone;
    String email;
    String status;


    public User(String id, String username, String imageUrl, String phone ,String email ,String status) {
        this.id = id;
        this.username = username;
        this.imageUrl = imageUrl;
        this.phone = phone;
        this.email=email;
        this.status=status;
    }
    public User(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
