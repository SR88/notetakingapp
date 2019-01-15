package com.example.ssneddon.notetakingapp.entity;

public class Term {

    private int key;
    private String termName;
    private String dateStart;
    private String dateEnd;

    public Term() {
    }

    public Term(int key, String termName, String dateStart, String dateEnd) {
        this.key = key;
        this.termName = termName;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
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
}
