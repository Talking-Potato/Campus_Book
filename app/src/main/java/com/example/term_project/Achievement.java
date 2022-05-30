package com.example.term_project;

public class Achievement {

    private String title;
    private String date;
    private Integer exp;
    private Boolean canObtainExp = true;

    public Achievement() {
    }

    public Achievement(String title, String date) {
        this.title = title;
        this.date = date;
        this.exp = 50;
    }

    public Achievement(String title, String date, int exp) {
        this.title = title;
        this.date = date;
        this.exp = 50;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Boolean getCanObtainExp() {
        return canObtainExp;
    }
    public void setCanObtainExp(Boolean b) {
        this.canObtainExp = b;
    }
}
