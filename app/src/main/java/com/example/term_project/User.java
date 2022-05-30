package com.example.term_project;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    public String userName;
    public String schoolID;

    public User() {
    }

    public User(String userName, String schoolID) {
        this.userName = userName;
        this.schoolID = schoolID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", userName);
        result.put("schoolID", schoolID);
        return result;
    }
}
