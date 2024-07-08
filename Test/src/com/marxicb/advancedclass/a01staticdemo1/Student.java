package com.marxicb.advancedclass.a01staticdemo1;

public class Student extends Person{
    private String name;
    private int age;
    private String gender;

    public Student() {
    }
    @Override
    void eat(){
        System.out.println("Student eat");
    }
    public Student(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
