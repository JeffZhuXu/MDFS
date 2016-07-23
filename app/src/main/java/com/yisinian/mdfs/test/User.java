package com.yisinian.mdfs.test;

import java.io.Serializable;

/**
 * Created by mac on 16/1/4.
 */
public class User implements Serializable{
    public String name;
    public int age;

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public User(String name,int age){
        this.name=name;
        this.age=age;
    }
}
