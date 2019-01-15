package com.example.ssneddon.notetakingapp.entity;

public class Course {

    private int key;
    private String name;
    private String dateStart;
    private String dateEnd;
    private Term term;

    public Course() {
    }

    public Course(int key, String name, String dateStart, String dateEnd, Term term) {
        this.key = key;
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.term = term;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }
}
