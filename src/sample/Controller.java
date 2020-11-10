package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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

    ObservableList<Student> students = FXCollections.observableArrayList();
    ObservableList<Course> courses = FXCollections.observableArrayList();
    ObservableList<Grade> grades = FXCollections.observableArrayList();
    ObservableList<Grade> studentGrade = FXCollections.observableArrayList();

    public void addStudent(ActionEvent actionEvent) {
        Student student = new Student(textFieldFirstName.getText(),textFieldLastName.getText(),textFieldStudentID.getText(), textFieldHometown.getText());
        students.add(student);

        System.out.println(students);
    }

    //Will be executed only when GUI is ready
    public void initialize() {
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
        comboBoxStudentsForGrade.setItems(students);
        comboBoxGradesForStudent.setItems(grades);

        courses.addAll(new Course("ES1"),new Course("SD19"), new Course("SD20"));
        grades.addAll(new Grade("03"),new Grade("02"),new Grade("4"),new Grade("7"),new Grade("10"), new Grade("12"));

    }

    public void AddStudentToCourse(ActionEvent actionEvent) {
        //Actually enroll the students
        //In tab three you should be able to see the enrolled students
        Course course = (Course) comboBoxCourses.getSelectionModel().getSelectedItem();
        Student student = (Student) comboBoxStudents.getSelectionModel().getSelectedItem();
        course.enrollStudent(student);
        //System.out.println(comboBoxCourses.getSelectionModel().getSelectedItem());
        //System.out.println(comboBoxStudents.getSelectionModel().getSelectedItem());
        System.out.println(course.getName() + ": " + course.getEnrolledStudents());
    }

    public void AddGradeToStudent(ActionEvent actionEvent) {
        Course course = (Course) comboBoxCoursesForGrade.getSelectionModel().getSelectedItem();
        Student student = (Student) comboBoxStudentsForGrade.getSelectionModel().getSelectedItem();
        Grade grade = (Grade) comboBoxGradesForStudent.getSelectionModel().getSelectedItem();
        studentGrade.add(new Grade(student.getStudentID(),grade.getGrade()));
        System.out.println(student.getFirstName() + " has grade " + grade.getGrade());

    }
}
