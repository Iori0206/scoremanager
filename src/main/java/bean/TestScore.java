package bean;

import java.io.Serializable;

public class TestScore implements Serializable {
    private Student student;    // 学生情報
    private Subject subject;    // 科目情報
    private School school;      // 学校情報
    private int num;           // 回数 (DBのCLASS_NUMに対応)
    private int point;         // 得点

    // --- ゲッターとセッター ---

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    // Actionでスペルミス(setSubiect)があった箇所です。ここを正しい綴りにします。
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

    // 必要に応じて、以前あった個別のゲッターも残しておくと安全です
    public String getStudentNo() {
        return student != null ? student.getNo() : null;
    }

    public String getStudentName() {
        return student != null ? student.getName() : null;
    }
}