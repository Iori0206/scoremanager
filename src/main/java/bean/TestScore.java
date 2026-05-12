package bean;

import java.io.Serializable;

public class TestScore implements Serializable {

    private Student student;
    private Subject subject;
    private School school;
    private int num;
    private int point;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getStudentNo() {
        return student == null ? null : student.getNo();
    }

    public String getStudentName() {
        return student == null ? null : student.getName();
    }

    public String getSubjectCd() {
        return subject == null ? null : subject.getCd();
    }

    public String getSubjectName() {
        return subject == null ? null : subject.getName();
    }

    public String getSchoolCd() {
        return school == null ? null : school.getCd();
    }
}