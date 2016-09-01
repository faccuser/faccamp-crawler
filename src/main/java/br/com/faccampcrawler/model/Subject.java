package br.com.faccampcrawler.model;

import java.util.List;

public class Subject {
    private String name;
    private String partialAverage;
    private String finalAverage;
    private String absences;
    private List<Grade> grades;
    private List<Grade> edpGrades;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartialAverage() {
        return partialAverage;
    }

    public void setPartialAverage(String partialAverage) {
        this.partialAverage = partialAverage;
    }

    public String getFinalAverage() {
        return finalAverage;
    }

    public void setFinalAverage(String finalAverage) {
        this.finalAverage = finalAverage;
    }

    public String getAbsences() { return absences; }

    public void setAbsences(String absences) {
        this.absences = absences;
    }

    public List getGrades() {
        return grades;
    }

    public void setGrades(List grades) {
        this.grades = grades;
    }

    public List getEdpGrades() {
        return edpGrades;
    }

    public void setEdpGrades(List edpGrades) {
        this.edpGrades = edpGrades;
    }
}
