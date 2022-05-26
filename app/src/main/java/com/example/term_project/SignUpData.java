package com.example.term_project;

public class SignUpData {
    String name;
    String schoolID;

    public SignUpData() {
    }

    //getter, setter 설정
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    public SignUpData(String name, String schoolID) {
        this.name = name;
        this.schoolID = schoolID;
    }
}