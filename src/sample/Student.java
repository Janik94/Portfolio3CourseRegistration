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

    //A student has an array list of attendedCourses given by CourseID,
    //an array list of attendedCourses given by course name,
    //an array list of this students grade in their attended courses at the corresponding index,
    //an array list of which the average grade of the attended courses at the corresponding index.

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


    public String getLastName() {
        return lastName;
    }


    public String getStudentID() {
        return studentID;
    }

    public String getHometown() {
        return hometown;
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

    //The name of attended courses for the students is created through a prepared statement nad SQL query.
    public void nameOfAttendedCourse(SQLStatement SQLStatement) throws SQLException {
        for (int i = 0; i < attendedCourses.size();i++)
        nameOfAttendedCourses.add(SQLStatement.courseNamePreparedStatement(attendedCourses.get(i).getCourseID(), studentID));

    }

    public ObservableList<String> getNameOfAttendedCourses() {
        return nameOfAttendedCourses;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}
