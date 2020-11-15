package sample;

public class Grade {
    private String grade;
    private String studentID;
    private String courseName;

    public Grade(String grade, String studentID) {
        this.grade = grade;
        this.studentID = studentID;
    }

    public Grade() {
        this.grade = null;
    }

    public Grade(String grade) {
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName= courseName;
    }

    @Override
    public String toString() {
        return grade;
    }
}
