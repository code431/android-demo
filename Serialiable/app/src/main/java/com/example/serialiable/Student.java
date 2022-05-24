package com.example.serialiable;

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = -8925853683462565401L;//将类序列化
    private String name;
    //  private transient String name; //transient可以让某个变量不被序列化
    private int age;
    private Score score;

    public Student(String name, int age, Score score) {
        this.name = name;
        this.age = age;
        this.score = score;
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

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

}

class Score implements Serializable {
    private static final long serialVersionUID = 8368668208605809783L;
    private int Math;
    private int English;
    private int Chinese;
    private String grade;

    public Score(int math, int english, int chinese) {
        Math = math;
        English = english;
        Chinese = chinese;
        if (math > 90 && english > 90 && chinese > 90) {
            this.grade = "A";
        } else if (math > 80 && english > 80 && chinese > 80) {
            this.grade = "B";
        } else {
            this.grade = "C";
        }
    }

    public int getMath() {
        return Math;
    }

    public void setMath(int math) {
        Math = math;
    }

    public int getEnglish() {
        return English;
    }

    public void setEnglish(int english) {
        English = english;
    }

    public int getChinese() {
        return Chinese;
    }

    public void setChinese(int chinese) {
        Chinese = chinese;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
