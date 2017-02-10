package com.htlconline.sm.classmate.Batch.Data;

import java.util.List;

/**
 * Created by Anurag on 26-01-2017.
 */

public class BatchFilterResults {
    private List<String> Instructors;
    private List<String> Academic_Year;
    private List<String> Centres;
    private List<String> Zones;
    private List<String> Classes;
    private List<String> Products;
    private List<String> Subjects;

    public List<String> getInstructors() {
        return Instructors;
    }

    public void setInstructors(List<String> instructors) {
        Instructors = instructors;
    }

    public List<String> getAcademic_Year() {
        return Academic_Year;
    }

    public void setAcademic_Year(List<String> academic_Year) {
        Academic_Year = academic_Year;
    }

    public List<String> getCentres() {
        return Centres;
    }

    public void setCentres(List<String> centres) {
        Centres = centres;
    }

    public List<String> getZones() {
        return Zones;
    }

    public void setZones(List<String> zones) {
        Zones = zones;
    }

    public List<String> getClasses() {
        return Classes;
    }

    public void setClasses(List<String> classes) {
        Classes = classes;
    }

    public List<String> getProducts() {
        return Products;
    }

    public void setProducts(List<String> products) {
        Products = products;
    }

    public List<String> getSubjects() {
        return Subjects;
    }

    public void setSubjects(List<String> subjects) {
        Subjects = subjects;
    }
}
