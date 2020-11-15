package sample;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class GradeModel {
    Connection connection = null;
    Statement statement = null;
    String url;

    public GradeModel(String url) {
        this.url = url;
    }

    //Attempts to establish a connection to the given database URL of the form jdbc:subprotocol:subname.
    public void connect() throws SQLException{
        connection = getConnection(this.url);
    }

    //Creates a Statement object for sending SQL statements to the database.
    public void createStatement() throws SQLException{
        this.statement = connection.createStatement();
    }

    //Returns an array list of "Student"-objects with all the students from the database (table studentInfo).
    //statement.executeQuery is written inside a Try-Catch statement because it throws an SQLException.
    public ArrayList<Student> StudentQueryStatement(){
        ArrayList<Student> students = new ArrayList<Student>();
        String sql = "select studentID, firstName, lastName, hometown\n" +
                "from studentInfo";
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery(sql);
            while (resultSet !=null && resultSet.next()){
                String studentId = resultSet.getString(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String hometown = resultSet.getString(4);
                students.add(new Student(firstName,lastName,studentId,hometown));
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return students;
    }

    //Takes as input of courseID and studentID. Creates and returns an array list of "Grade" objects with
    //this students (studentID) grade for this course (CourseID).
    public ArrayList<Grade> studentGradePreparedStatement(String studentId, String courseID) throws SQLException {
        ArrayList<Grade> grades = new ArrayList<>();
        String sql = "select grade\n" +
                "from studentCourseGrade\n" +
                "where studentID is ? and courseID is ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,studentId);
        preparedStatement.setString(2,courseID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet == null){
            System.out.println("No data retrieved");
        }
        while (resultSet != null && resultSet.next()){
            String grade = resultSet.getString(1);
            grades.add(new Grade(grade,studentId));
        }
        return grades;
    }

    //Returns an array list of "Grade" object withe the grades from the database where the courseID is the input courseID.
    public ArrayList<Grade> CourseGradePreparedStatement(String courseID) throws SQLException {
        ArrayList<Grade> grades = new ArrayList<>();
        String sql = "select grade, studentID\n" +
                "from studentCourseGrade\n" +
                "where courseID is ? ;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,courseID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet == null){
            System.out.println("No data retrieved");
        }
        while (resultSet != null && resultSet.next()){
            String grade = resultSet.getString(1);
            String studentID = resultSet.getString(2);
            grades.add(new Grade(grade, studentID));
        }
        return grades;
    }

    //returns the average grade specified in the input sql String for that particular id (either CourseID or StudentID).
    public String getAverage(String id, String sql) throws SQLException {
        String grade = null;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet == null){
            System.out.println("No data retrieved");
        }
        while (resultSet != null && resultSet.next()){
            grade = resultSet.getString(1);
    }
        return grade;
    }

    //Helper method for getAverage that sets the SQL statement to be the one that will find the average of the courses grades.
    //In case the average is 0.0 it means that the students of this course have not been graded yet and therefore it returns "on going",
    public String courseAVGPreparedStatement( String id) throws SQLException{
        String sql="select AVG(grade)\n" +
                "from studentCourseGrade\n" +
                "where courseID is ?;";
        String result = getAverage(id,sql);
        if (result.equals("0.0")) return "On going";
        return result;
    }

    //Helper method for getAverage that sets the SQL statement to be the one that will find the average of the students
    // grades for all the subject the student has been graded.
    public String studentAVGPreparedStatement( String id) throws SQLException{
        String sql="select AVG(grade)\n" +
                "from studentCourseGrade\n" +
                "where grade != 'On going' and studentID is ?;";

        return getAverage(id,sql);
    }
}
