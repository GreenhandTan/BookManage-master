package com.gx.domain;

import lombok.Data;
//这里我们使用了lombook插件，一个@Data注解就可以搞定
@Data
public class User {
    private int id;
    private String username;
    private String password;
    private int grade;//角色

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
