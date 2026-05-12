package bean;

import java.io.Serializable;

public class TestScore implements Serializable {

    // 学生情報
    private Student student;

    // 科目情報
    private Subject subject;

    // 学校情報
    private School school;

    // 回数
    private int num;

    // 得点
    private int point;

    // -------------------------
    // getter / setter
    // -------------------------

    public Student getStudent() {

        return student;
    }

    public void setStudent(
            Student student
    ) {

        this.student = student;
    }

    public Subject getSubject() {

        return subject;
    }

    public void setSubject(
            Subject subject
    ) {

        this.subject = subject;
    }

    public School getSchool() {

        return school;
    }

    public void setSchool(
            School school
    ) {

        this.school = school;
    }

    // 回数
    public int getNum() {

        return num;
    }

    public void setNum(
            int num
    ) {

        this.num = num;
    }

    // 点数
    public int getPoint() {

        return point;
    }

    public void setPoint(
            int point
    ) {

        this.point = point;
    }

    // -------------------------
    // 追加getter
    // -------------------------

    public String getStudentNo() {

        if (student == null) {

            return null;
        }

        return student.getNo();
    }

    public String getStudentName() {

        if (student == null) {

            return null;
        }

        return student.getName();
    }

    public String getSubjectCd() {

        if (subject == null) {

            return null;
        }

        return subject.getCd();
    }

    public String getSubjectName() {

        if (subject == null) {

            return null;
        }

        return subject.getName();
    }

    public String getSchoolCd() {

        if (school == null) {

            return null;
        }

        return school.getCd();
    }
}