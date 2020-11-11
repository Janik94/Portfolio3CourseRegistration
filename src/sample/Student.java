package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Student {
    private String firstName;
    private String lastName;
    private String studentID;
    private String hometown;
    private ObservableList<Course> attendedCourses = FXCollections.observableArrayList();

    public ObservableList<Course> getAttendedCourses() {
        return attendedCourses;
    }

    public void setAttendedCourses(ObservableList<Course> attendedCourses) {
        this.attendedCourses = attendedCourses;
    }


    public Student(String firstName, String lastName, String studentID, String hometown) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = studentID;
        this.hometown = hometown;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String info() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", studentID='" + studentID + '\'' +
                '}';
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
