package org.openjfx.srms;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 *
 */
public class Student {
    public int id;
    public String email_id;
    public String final_grade;//final_grade
    public String student_name;//student_name
    public int age;//age
    public int roll_no;//roll_no

    public Student() {
    }

    public Student(int id, String email_id, String final_grade, String student_name, int age, int roll_no) {
        this.id = id;
        this.email_id = email_id;
        this.final_grade = final_grade;
        this.student_name = student_name;
        this.age = age;
        this.roll_no = roll_no;
    }

    public int getId() {
        return id;
    }

    public String getEmail_id() {
        return email_id;
    }

    public String getfinal_grade() {
        return final_grade;
    }

    public String getstudent_name() {
        return student_name;
    }

    public int getage() {
        return age;
    }

    public int getroll_no() {
        return roll_no;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public void setfinal_grade(String final_grade) {
        this.final_grade = final_grade;
    }

    public void setstudent_name(String student_name) {
        this.student_name = student_name;
    }

    public void setage(int age) {
        this.age = age;
    }

    public void setroll_no(int roll_no) {
        this.roll_no = roll_no;
    }
    
    
}
