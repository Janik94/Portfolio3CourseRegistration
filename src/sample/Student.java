package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class Student {
    private String firstName;
    private String lastName;
    private String studentID;
    private String hometown;
    private String myAverage;

    //A student will have an array list of attendedCourses
    //and an array list of this students grade in their attended courses at the corresponding index,
    //and an array list of which the average grade of the attended courses at the corresponding index.
    private ObservableList<Course> attendedCourses = FXCollections.observableArrayList();
    private ObservableList<String> nameOfAttendedCourses = FXCollections.observableArrayList();
    private ObservableList<Grade> myGradeinAttendedCourses = FXCollections.observableArrayList();
    private ObservableList<String> averageGradeOfAttendedCourses = FXCollections.observableArrayList();

    public Student(String firstName, String lastName, String studentID, String hometown) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = studentID;
        this.hometown = hometown;
    }

    public Student() {
        firstName = "null";
        lastName = "null";
        studentID = "null";
        hometown = "null";
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

    //A students average is calculated through a prepared statement and SQL query.
    public String myAverageGrade(SQLStatement SQLStatement) throws SQLException {
        myAverage = SQLStatement.studentAVGPreparedStatement(studentID);
        return myAverage;
    }

    public String getMyAverage() {
        return myAverage;
    }

    public ObservableList<Grade> getMyGradeinAttendedCourses() {
        return myGradeinAttendedCourses;
    }

    public ObservableList<String> getAverageGradeOfAttendedCourses() {
        return averageGradeOfAttendedCourses;
    }

    public ObservableList<Course> getAttendedCourses() {
        return attendedCourses;
    }

    public void setAttendedCourses(ObservableList<Course> attendedCourses) {
        this.attendedCourses = attendedCourses;
    }

    public ObservableList<String> nameOfAttendedCourse(SQLStatement SQLStatement) throws SQLException {
        for (int i = 0; i < attendedCourses.size();i++)
        nameOfAttendedCourses.add(SQLStatement.courseNamePreparedStatement(attendedCourses.get(i).getCourseID(), studentID));
        return  nameOfAttendedCourses;
    }

    public ObservableList<String> getNameOfAttendedCourses() {
        return nameOfAttendedCourses;
    }

    public void setNameOfAttendedCourses(ObservableList<String> nameOfAttendedCourses) {
        this.nameOfAttendedCourses = nameOfAttendedCourses;
    }
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
