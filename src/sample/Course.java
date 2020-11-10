package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Course {
    private String name;
    private ObservableList<Student> enrolledStudents = FXCollections.observableArrayList();

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

    public boolean enrollStudent(Student student){
        return enrolledStudents.add(student);
    }
    @Override
    public String toString() {
        return name;
    }
}
