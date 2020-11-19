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

    @Override
    public String toString() {
        return grade;
    }
}
