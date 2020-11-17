package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Course {

    private String courseID;

    //A course has an observable array list of all the enrolled students.
    //and in another observable list the grade of that student at the corresponding index
    private ObservableList<Student> enrolledStudents = FXCollections.observableArrayList();
    private ObservableList<Grade> gradesOfStudents = FXCollections.observableArrayList();


    public Course(String name) {
        this.courseID = name;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setName(String name) {
        this.courseID = name;
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
    //A method that returns the average grade of the course through a prepared statement and SQL query.
    public String averageGradeOfCourse(SQLStatement SQLStatement) throws SQLException {
        return SQLStatement.courseAVGPreparedStatement(this.courseID);
    }

    public boolean enrollStudent(Student student){
        return enrolledStudents.add(student);
    }

    @Override
    public String toString() {
        return courseID;
    }
}
