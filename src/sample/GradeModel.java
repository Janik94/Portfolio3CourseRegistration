package sample;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class GradeModel {
    Connection connection = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    String url;

    public GradeModel(String url) {
        this.url = url;
    }

    public void connect() throws SQLException{
        connection = getConnection(this.url);
    }

    public void createStatement() throws SQLException{
        this.statement = connection.createStatement();
    }

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


    public ArrayList<Grade> CourseGradeQueryStatement(String sql){
        ArrayList<Grade> grades = new ArrayList<Grade>();
        /*String sql ="select studentID, gradeSD19\n" +
                "from studentWithGrades\n" +
                "where gradeSD19 IS NOT NULL;";

         */
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery(sql);
            while (resultSet != null && resultSet.next()){
                String studentID = resultSet.getString(1);
                String grade = resultSet.getString(2);
                grades.add(new Grade(grade, studentID));
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return grades;
    }
    //Make method that puts grades into a student or course

}
