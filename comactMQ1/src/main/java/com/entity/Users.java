package com.entity;

import java.io.Serializable;

public class Users implements Serializable {

    private String userId;
    private String userName;
    private String sex;
    private String age;
    private String type;


    public Users() {
        super();
    }

    public Users(String userId, String userName, String sex, String age,
                 String type) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.sex = sex;
        this.age = age;
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Users [userId=" + userId + ", userName=" + userName + ", sex="
                + sex + ", age=" + age + ", type=" + type + "]";
    }


}
