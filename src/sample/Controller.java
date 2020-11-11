package sample;

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


    public void addStudent(ActionEvent actionEvent) {
        Student student = new Student(textFieldFirstName.getText(),textFieldLastName.getText(),textFieldStudentID.getText(), textFieldHometown.getText());
        students.add(student);

        System.out.println(students);
    }

    //Will be executed only when GUI is ready
    public void initialize() {
        courses.addAll(new Course("ES1"),new Course("SD19"), new Course("SD20"));
        grades.addAll(new Grade("-3"),new Grade("00"), new Grade("02"),new Grade("4"),new Grade("7"),new Grade("10"), new Grade("12"));
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
        courses.get(0).getGradesOfStudents().addAll(gradeModel.CourseQueryStatement(sql1));
        String sql2 = "select studentID, gradeSD19\n" +
                "from studentWithGrades\n" +
                "where gradeSD19 is not null;";
        courses.get(1).getGradesOfStudents().addAll(gradeModel.CourseQueryStatement(sql2));
        String sql3 = "select studentID, gradeSD20\n" +
                "from studentWithGrades\n" +
                "where gradeSD20 is not null;";
        courses.get(2).getGradesOfStudents().addAll(gradeModel.CourseQueryStatement(sql3));
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


        //courses.get(0).getEnrolledStudents()
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

        comboBoxStudents.setItems(students);
        comboBoxCourses.setItems(courses);

        comboBoxCoursesForGrade.setItems(courses);
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

        comboBoxStudentsInfo.setItems(students);


        comboBoxGradesForStudent.setItems(grades);
        comboBoxStudentsInfo.setItems(students);


        ES19_addStudent.setItems(courses.get(0).getEnrolledStudents());
        SD19_addStudent.setItems(courses.get(1).getEnrolledStudents());
        SD20_addStudent.setItems(courses.get(2).getEnrolledStudents());

        ES19_StudentID.setItems(courses.get(0).getEnrolledStudents());
        SD19_StudentID.setItems(courses.get(1).getEnrolledStudents());
        SD20_StudentID.setItems(courses.get(2).getEnrolledStudents());

        ES19_grades.setItems(courses.get(0).getGradesOfStudents());
        SD19_grades.setItems(courses.get(1).getGradesOfStudents());
        SD20_grades.setItems(courses.get(2).getGradesOfStudents());
        }

    public void AddStudentToCourse(ActionEvent actionEvent) {
        //Actually enroll the students
        //In tab three you should be able to see the enrolled students
        //Course course = (Course) comboBoxCourses.getSelectionModel().getSelectedItem();
        //Student student = (Student) comboBoxStudents.getSelectionModel().getSelectedItem();
        //course.enrollStudent(student);
        //System.out.println(comboBoxCourses.getSelectionModel().getSelectedItem());
        //System.out.println(comboBoxStudents.getSelectionModel().getSelectedItem());
        //System.out.println(course.getName() + ": " + course.getEnrolledStudents());

        Course course = (Course) comboBoxCourses.getSelectionModel().getSelectedItem();
        Student student = (Student) comboBoxStudents.getSelectionModel().getSelectedItem();
        course.getEnrolledStudents().add(student);
        student.getAttendedCourses().add(course);
        System.out.println(course.getName()+ " has students: "+course.getEnrolledStudents());

    }

    public void AddGradeToStudent(ActionEvent actionEvent) {
        Course course = (Course) comboBoxCoursesForGrade.getSelectionModel().getSelectedItem();
        Student student = (Student) comboBoxStudentsForGrade.getSelectionModel().getSelectedItem();
        Grade grade = (Grade) comboBoxGradesForStudent.getSelectionModel().getSelectedItem();

        course.getGradesOfStudents().add(grade);
        course.getEnrolledStudents().add(student);

        System.out.println(student.getFirstName() + " has grade " + grade.getGrade());

    }


    public void checkStudent(ActionEvent actionEvent) {
        Student student = (Student) comboBoxStudentsInfo.getSelectionModel().getSelectedItem();
        //If student is in course add course to listView
    }
}
