package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    public ComboBox comboBoxStudentsInfo;
    public ListView SD19_addStudent;
    public ListView SD20_addStudent;
    public ListView ES19_addStudent;
    public ListView SD19_StudentID;
    public ListView SD20_StudentID;
    public ListView ES19_StudentID;
    public ListView SD19_grades;
    public ListView SD20_grades;
    public ListView ES19_grades;
    public ListView CoursesTaken;
    public ListView AvgCourseGrade;
    public ListView StudentsGrade;
    public Label AverageStudentGrade;
    public Label AverageGradeForSelectedStudent;
    public Label SelectedStudentsGrades;

    ObservableList<Student> students = FXCollections.observableArrayList();
    ObservableList<Course> courses = FXCollections.observableArrayList();
    ObservableList<Grade> grades = FXCollections.observableArrayList();
    ObservableList<String> AVGStudent = FXCollections.observableArrayList();
    ObservableList<String> AVGCourse = FXCollections.observableArrayList();


    public void initialize() throws SQLException {
        //All the course options and grade options are added.
        courses.addAll(new Course("SD19"), new Course("SD20"), new Course("ES1"));
        grades.addAll(new Grade("-3"),new Grade("00"), new Grade("02"),new Grade("4"),new Grade("7"),new Grade("10"), new Grade("12"));
        databaseInclusion();
        fillTab2();
        fillTab3();
        fillTab4();
        fillTab5();
    }

    //Takes the information from the database through SQL queries and adds it to this program.
    public void databaseInclusion() throws SQLException {
        //Clears the array list students, and then clears the two array lists containing the
            // enrolled Students and grades for all enrolled students for all courses.
        students.clear();

        for (int i = 0; i < courses.size(); i++){
            courses.get(i).getEnrolledStudents().clear();
            courses.get(i).getGradesOfStudents().clear();
        }
        //Adds all Students from the database to the observable array list students.
        SQLStatement SQLStatement = initSQLStatement();
        students.addAll(SQLStatement.studentQueryStatement());


        //Fills Course.enrolledStudents and Student.attendedCourses.
        databaseEnrollStudents();


        //Fills an array list of grades of enrolled students for every course.
            for (int i = 0; i < courses.size(); i++){
                courses.get(i).getGradesOfStudents().addAll(SQLStatement.courseGradePreparedStatement(courses.get(i).getCourseID()));
            }

        //Fills the list of the average grade for all the enrolled student for each course at the corresponding index of courses.
        for (int i = 0; i < courses.size(); i++){
            AVGCourse.add(courses.get(i).averageGradeOfCourse(SQLStatement));
        }

        //Fills the list of the average grade for all the students' attended courses for each student at the corresponding index of students.
        for (int i = 0; i < students.size(); i++){
            AVGStudent.add(students.get(i).myAverageGrade(SQLStatement));
        }


        //Fills the list of the student's grades of their attended courses for all students
        for (int i = 0; i < students.size(); i++) {
            for (int j = 0; j < students.get(i).getAttendedCourses().size(); j++) {
                students.get(i).getMyGradeinAttendedCourses().addAll(SQLStatement.studentGradePreparedStatement(students.get(i).getStudentID(), students.get(i).getAttendedCourses().get(j).getCourseID()));
            }
        }

        //For each student,the average grade of their attended courses is added to an array list at the corresponding index.
        for (int i=0; i < students.size(); i++) {
            for (int j=0; j < students.get(i).getAttendedCourses().size();j++)
                students.get(i).getAverageGradeOfAttendedCourses().addAll(SQLStatement.courseAVGPreparedStatement(students.get(i).getAttendedCourses().get(j).getCourseID()));
            }
        //For each student the names of their attended courses are added here.
        // This is used mainly for aestic purposes for the GUI, instead of printing the course ID.
        for (int i= 0; i < students.size();i++) {
            students.get(i).nameOfAttendedCourse(SQLStatement);
        }

    }

    //Attempts to establish a connection to the given database URL of the form jdbc:subprotocol:subname
    //and creates a Statement object for sending SQL statements to the database and returns this Statement object.
    public SQLStatement initSQLStatement() {
        //Insert your URL here in the form of the form jdbc:subprotocol:subname to the StudentsGrades.db file.
        //String url = "jdbc:sqlite:C:\\Users\\WinSa\\OneDrive\\Dokumenter\\RUC\\Fifth semester\\Software Development\\Programs from class\\Portfolio3CourseRegistration\\StudentsGrades.db";
        String url = "jdbc:sqlite:/Users/namra/Documents/GitHub/Portfolio3CourseRegistration/StudentsGrades.db";

        SQLStatement SQLStatement = new SQLStatement(url);
        try {
            SQLStatement.connect();
            SQLStatement.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return SQLStatement;
    }

    //The list of attended courses for all students, and the list of enrolled students for all courses is filled.
    public void databaseEnrollStudents() throws SQLException{
        //All methods about SQL statements are made in class SQLStatement.
        //initializes an object SQLStatement.
        SQLStatement SQLStatement = initSQLStatement();

        //For every course the list of enrolled students is filled.
        for (int i= 0; i <courses.size();i++) {
            courses.get(i).getEnrolledStudents().addAll(SQLStatement.studentEnrolledPreparedStatement(courses.get(i).getCourseID()));
        }

        //For every student the list of attended courses is filled.
        for (int i = 0; i < students.size(); i++){
            students.get(i).getAttendedCourses().addAll(SQLStatement.studentAttendedCourses(students.get(i).getStudentID()));
        }
    }




    // Tab 1 contains four text fields where you can write the information of a new Student and
    // add that person to the database ,when the button is pressed. (This method addStudent is does this action).
    public void addStudent(ActionEvent actionEvent) throws SQLException {
        SQLStatement SQLStatement = initSQLStatement();
        String firstName = textFieldFirstName.getText();
        String lastName = textFieldLastName.getText();
        String studentID = textFieldStudentID.getText();
        String hometown = textFieldHometown.getText();

        SQLStatement.addNewStudent(firstName,lastName,studentID,hometown);
        databaseInclusion();
    }

    //Tab 2 contains a table that views all the information from the array list students,
    // each of their attributes displayed in different columns.
    public void fillTab2(){

        tableStudents.setItems(students);
        //Column 1 will fill out with the getFirstName for all students in the array list students.
        tableColumnFirstName.setCellValueFactory(
                new PropertyValueFactory<Student, String>("firstName")
        );
        //Column 2 will fill out with the getLastName for all students in the array list students.
        tableColumnLastName.setCellValueFactory(
                new PropertyValueFactory<Student, String>("lastName")
        );
        //Column 3 will fill out with the getStudentID for all students in the array list students.
        tableColumnStudentID.setCellValueFactory(
                new PropertyValueFactory<Student, String>("studentID")
        );
        //Column 4 will fill out with the getHometown for all students in the array list students.
        tableColumnHometown.setCellValueFactory(
                new PropertyValueFactory<Student, String>("hometown")
        );
    }

    //Tab 3 contains a top part where you can choose a student and enroll them onto a course by pressing button.
    //the center and bottom part contains overviews of which students are enrolled in the different courses.
    public void fillTab3(){

        //We set the comboxed to contain the students and the courses.
        comboBoxStudents.setItems(students);
        comboBoxCourses.setItems(courses);

        //The bottom List Views are set to show the list of students for all  courses.
        SD19_addStudent.setItems(courses.get(0).getEnrolledStudents());
        SD20_addStudent.setItems(courses.get(1).getEnrolledStudents());
        ES19_addStudent.setItems(courses.get(2).getEnrolledStudents());
    }

    //Tab 3 button
    //This button enrolls a student to a course.
    public void addStudentToCourse(ActionEvent actionEvent) throws SQLException {
        Course course = (Course) comboBoxCourses.getSelectionModel().getSelectedItem();
        Student student = (Student) comboBoxStudents.getSelectionModel().getSelectedItem();
        course.getEnrolledStudents().add(student);
        student.getAttendedCourses().add(course);
        SQLStatement SQLStatement = initSQLStatement();
        SQLStatement.addStudentToCourse(student.getStudentID(),course.getCourseID(),"On going");
        databaseInclusion();
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
            }
        }
        );
        SD19_StudentID.setItems(courses.get(0).getEnrolledStudents());
        SD20_StudentID.setItems(courses.get(1).getEnrolledStudents());
        ES19_StudentID.setItems(courses.get(2).getEnrolledStudents());

        SD19_grades.setItems(courses.get(0).getGradesOfStudents());
        SD20_grades.setItems(courses.get(1).getGradesOfStudents());
        ES19_grades.setItems(courses.get(2).getGradesOfStudents());
    }
    public void fillTab5(){
        comboBoxStudentsInfo.setItems(students);

        comboBoxStudentsInfo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                Student selectedStudent = (Student) t1;
                CoursesTaken.setItems(selectedStudent.getNameOfAttendedCourses());
                //We want the particular grade this particular student got for the attended courses
                StudentsGrade.setItems(selectedStudent.getMyGradeinAttendedCourses());
                AvgCourseGrade.setItems(selectedStudent.getAverageGradeOfAttendedCourses());
                AverageStudentGrade.setText(selectedStudent.getMyAverage());
                AverageGradeForSelectedStudent.setText(selectedStudent.getFirstName()+" "+selectedStudent.getLastName()+ "'s average is: ");
                SelectedStudentsGrades.setText(selectedStudent.getFirstName()+" "+selectedStudent.getLastName()+ "'s grades: ");
            }
        }
        );

    }


    //Tab 4 button
    //This button adds a grade to a student enrolled in that course.
    public void addGradeToStudent(ActionEvent actionEvent) throws SQLException {
        Course course = (Course) comboBoxCoursesForGrade.getSelectionModel().getSelectedItem();
        Student student = (Student) comboBoxStudentsForGrade.getSelectionModel().getSelectedItem();
        Grade grade = (Grade) comboBoxGradesForStudent.getSelectionModel().getSelectedItem();

        course.getGradesOfStudents().add(grade);
        course.getEnrolledStudents().add(student);

        SQLStatement SQLStatement = initSQLStatement();
        SQLStatement.changeGradeForStudent(student.getStudentID(),grade.getGrade());
        databaseInclusion();
    }
}