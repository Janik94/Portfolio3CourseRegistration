package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Course {
    private String name;
    private ObservableList<Student> enrolledStudents = FXCollections.observableArrayList();
    private ObservableList<Grade> gradesOfStudents = FXCollections.observableArrayList();


    public Course(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObservableList<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public ObservableList<Grade> getGradesOfStudents() {
        return gradesOfStudents;
    }

    public void setGradesOfStudents(ObservableList<Grade> gradesOfStudents) {
        this.gradesOfStudents = gradesOfStudents;
    }

    public Double averageGradeOfStudent(){
        ArrayList<Integer> gradeInt = new ArrayList<Integer>();
        double sum = 0;
        for (int i = 0; i < gradesOfStudents.size(); i++){
            Integer a = Integer.parseInt(gradesOfStudents.get(i).getGrade());
            gradeInt.add(a);
        }
        for (int j : gradeInt){
            sum += j;
        }
        return sum/gradeInt.size();
    }

    public boolean enrollStudent(Student student){
        return enrolledStudents.add(student);
    }

    @Override
    public String toString() {
        return name;
    }
}
