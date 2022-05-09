package com.example.term_project;

public class SignupData {
    String name;
    String schoolID;

    public SignupData() {
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

    public SignupData(String name, String schoolID) {
        this.name = name;
        this.schoolID = schoolID;
    }
}