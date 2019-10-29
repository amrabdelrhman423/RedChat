package com.example.chatred.Model;

public class Chat  {
    private String sende;
    private String recvier;
    private String message;
    private boolean isseen;

    public Chat(String sende, String recvier, String message ,boolean isseen) {
        this.sende = sende;
        this.recvier = recvier;
        this.message = message;
        this.isseen=isseen;
    }

    public Chat() {
    }

    public String getSende() {
        return sende;
    }

    public void setSende(String sende) {
        this.sende = sende;
    }

    public String getRecvier() {
        return recvier;
    }

    public void setRecvier(String recvier) {
        this.recvier = recvier;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }
}

