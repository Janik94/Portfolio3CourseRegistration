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

    public String courseAVGPreparedStatement( String id) throws SQLException{
        String sql="select AVG(grade)\n" +
                "from studentCourseGrade\n" +
                "where courseID is ?;";
        String result = getAverage(id,sql);
        if (result.equals("0.0")) return "On going";
        return result;
    }

    public String studentAVGPreparedStatement( String id) throws SQLException{
        String sql="select AVG(grade)\n" +
                "from studentCourseGrade\n" +
                "where grade != 'On going' and studentID is ?;";

        return getAverage(id,sql);
    }
}
