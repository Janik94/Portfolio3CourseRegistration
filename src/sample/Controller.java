package sample;

import com.sun.javafx.scene.control.SelectedCellsMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {
    public TextField textFieldFirstName;
    public TextField textFieldLastName;
    public TextField textFieldStudentID;
    public TextField textFieldHometown;
    public TableView tableStudents;
    public TableColumn tableColumnFirstName;
    public TableColumn tableColumnLastName;
    public TableColumn tableColumnStudentID;
    public TableColumn tableColumnHometown;
    public ComboBox comboBoxStudents;
    public ComboBox comboBoxCourses;
    public ComboBox comboBoxStudentsForGrade;
    public ComboBox comboBoxCoursesForGrade;
    public ComboBox comboBoxGradesForStudent;
    public ListView SD19_addStudent;
    public ListView SD20_addStudent;
    public ListView ES19_addStudent;
    public ListView SD19_StudentID;
    public ListView SD20_StudentID;
    public ListView ES19_StudentID;
    public ListView SD19_grades;
    public ListView SD20_grades;
    public ListView ES19_grades;
    public ComboBox comboBoxStudentsInfo;
    public ListView CoursesTaken;
    public ListView AvgCourseGrade;
    public ListView StudentsGrade;
    public Label AverageStudentGrade;

    ObservableList<Student> students = FXCollections.observableArrayList();
    ObservableList<Course> courses = FXCollections.observableArrayList();
    ObservableList<Grade> grades = FXCollections.observableArrayList();
    ObservableList<String> SelectedStudentsGrades = FXCollections.observableArrayList();
    ObservableList<Double> selectedStudentAVGCourse = FXCollections.observableArrayList();


    public void databaseInclusion(){
       //Add database to javaFX
        String url = "jdbc:sqlite:C:\\Users\\WinSa\\OneDrive\\Dokumenter\\RUC\\Fifth semester\\Software Development\\Programs from class\\Portfolio3CourseRegistration\\StudentsGrades.db";
        GradeModel gradeModel = new GradeModel(url);
        try{
            gradeModel.connect();
            gradeModel.createStatement();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        students.addAll(gradeModel.StudentQueryStatement());
        String sql1 = "select studentID, gradeES1\n" +
                "from studentWithGrades\n" +
                "where gradeES1 IS NOT NULL;";
        courses.get(0).getGradesOfStudents().addAll(gradeModel.CourseGradeQueryStatement(sql1));
        String sql2 = "select studentID, gradeSD19\n" +
                "from studentWithGrades\n" +
                "where gradeSD19 is not null;";
        courses.get(1).getGradesOfStudents().addAll(gradeModel.CourseGradeQueryStatement(sql2));
        String sql3 = "select studentID, gradeSD20\n" +
                "from studentWithGrades\n" +
                "where gradeSD20 is not null;";
        courses.get(2).getGradesOfStudents().addAll(gradeModel.CourseGradeQueryStatement(sql3));
        databaseEnrollStudents();
    }
    public void databaseEnrollStudents(){
        for (int m = 0; m <courses.size(); m++){
            for (int j = 0; j <students.size(); j++) { //Runs through all students
                for (int i = 0; i < courses.get(m).getGradesOfStudents().size(); i++) { //Runs through all students in the first course
                    String a = courses.get(m).getGradesOfStudents().get(i).getStudentID(); //Get student id of all students in the first course
                    if (students.get(j).getStudentID().equals(a)) { //compare studentId to the studentID of all student
                        courses.get(m).getEnrolledStudents().add(students.get(j)); //Enroll this student in course, if they have a grade for this course
                    }
                }
            }
        }

        //System.out.println(courses.get(2).getGradesOfStudents());
        for (int i = 0; i < students.size(); i++){
            for (int j = 0; j < courses.size(); j++) {
                if (courses.get(j).getEnrolledStudents().contains(students.get(i))) {
                    students.get(i).getAttendedCourses().add(courses.get(j));
                }
            }
        }
        for (int i = 0; i < students.size(); i++){
            System.out.println(students.get(i).getAttendedCourses());
        }
    }
    public void fillTab2(){
        tableStudents.setItems(students);
        tableColumnFirstName.setCellValueFactory(
                new PropertyValueFactory<Student, String>("firstName")
        );
        tableColumnLastName.setCellValueFactory(
                new PropertyValueFactory<Student, String>("lastName")
        );
        tableColumnStudentID.setCellValueFactory(
                new PropertyValueFactory<Student, String>("studentID")
        );
        tableColumnHometown.setCellValueFactory(
                new PropertyValueFactory<Student, String>("hometown")
        );
    }
    public void fillTab3(){
        comboBoxStudents.setItems(students);
        comboBoxCourses.setItems(courses);

        ES19_addStudent.setItems(courses.get(0).getEnrolledStudents());
        SD19_addStudent.setItems(courses.get(1).getEnrolledStudents());
        SD20_addStudent.setItems(courses.get(2).getEnrolledStudents());
    }
    public void fillTab4(){
        comboBoxCoursesForGrade.setItems(courses);
        comboBoxGradesForStudent.setItems(grades);
        Course selectedCourse = (Course) comboBoxCoursesForGrade.getSelectionModel().getSelectedItem();
        comboBoxCoursesForGrade.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {
                Course selectedCourse = (Course) t1;
                comboBoxStudentsForGrade.setItems(selectedCourse.getEnrolledStudents());
                System.out.println(t1);
            }
        }
        );
        ES19_StudentID.setItems(courses.get(0).getEnrolledStudents());
        SD19_StudentID.setItems(courses.get(1).getEnrolledStudents());
        SD20_StudentID.setItems(courses.get(2).getEnrolledStudents());

        ES19_grades.setItems(courses.get(0).getGradesOfStudents());
        SD19_grades.setItems(courses.get(1).getGradesOfStudents());
        SD20_grades.setItems(courses.get(2).getGradesOfStudents());
    }
    public void fillTab5(){
        comboBoxStudentsInfo.setItems(students);

        comboBoxStudentsInfo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                Student selectedStudent = (Student) t1;
                CoursesTaken.setItems(selectedStudent.getAttendedCourses());
                //We want the particular grade this particular student got for the attended courses
                for (int i = 0; i < selectedStudent.getAttendedCourses().size(); i++) {//We go trough all the courses selctedStudents attends
                    for (int j = 0; j < selectedStudent.getAttendedCourses().get(i).getGradesOfStudents().size(); j++) { //Runs through all students in the first course
                        if (selectedStudent.getAttendedCourses().get(i).getGradesOfStudents().get(j).getStudentID().equals(selectedStudent.getStudentID())) {
                            //then we want to fetch the grade SelectedStudent StudentID got in first course
                            SelectedStudentsGrades.add(selectedStudent.getAttendedCourses().get(i).getGradesOfStudents().get(j).getGrade());
                        }
                    }
                }
                selectedStudent.something();
                StudentsGrade.setItems(SelectedStudentsGrades);

                //System.out.println(selectedStudent.getAttendedCourses()+" has AVG grades "+ selectedStudent.getAverageGradeOfAttendedCourses());
                AvgCourseGrade.setItems(selectedStudent.getAverageGradeOfAttendedCourses());
                //System.out.println(selectedStudentAVGCourse);
            }
        }
        );
    }

    //Will be executed only when GUI is ready
    public void initialize() {
        courses.addAll(new Course("ES1"),new Course("SD19"), new Course("SD20"));
        grades.addAll(new Grade("-3"),new Grade("00"), new Grade("02"),new Grade("4"),new Grade("7"),new Grade("10"), new Grade("12"));
        databaseInclusion();
        fillTab2();
        fillTab3();
        fillTab4();
        fillTab5();
        }

    //Tab 1 button
    public void addStudent(ActionEvent actionEvent) {
        Student student = new Student(textFieldFirstName.getText(),textFieldLastName.getText(),textFieldStudentID.getText(), textFieldHometown.getText());
        students.add(student);

        System.out.println(students);
    }

    //Tab 3 button
    public void AddStudentToCourse(ActionEvent actionEvent) {
        Course course = (Course) comboBoxCourses.getSelectionModel().getSelectedItem();
        Student student = (Student) comboBoxStudents.getSelectionModel().getSelectedItem();
        course.getEnrolledStudents().add(student);
        student.getAttendedCourses().add(course);
        System.out.println(course.getName()+ " has students: "+course.getEnrolledStudents());

    }

    //Tab 4 button
    public void AddGradeToStudent(ActionEvent actionEvent) {
        Course course = (Course) comboBoxCoursesForGrade.getSelectionModel().getSelectedItem();
        Student student = (Student) comboBoxStudentsForGrade.getSelectionModel().getSelectedItem();
        Grade grade = (Grade) comboBoxGradesForStudent.getSelectionModel().getSelectedItem();

        course.getGradesOfStudents().add(grade);
        course.getEnrolledStudents().add(student);

        System.out.println(student.getFirstName() + " has grade " + grade.getGrade());

    }

    //Tab 5 button
    public void checkStudent(ActionEvent actionEvent) {
        Student student = (Student) comboBoxStudentsInfo.getSelectionModel().getSelectedItem();
        //If student is in course add course to listView
    }
}
