package bean;

import java.io.Serializable;

public class TestScore implements Serializable {
    // 追加：学生オブジェクトを丸ごと保持する変数
    private Student student; 
    
    private String studentNo;
    private String studentName;
    private int point;

    // --- 追加：studentのゲッターとセッター ---
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    // ------------------------------------

    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public int getPoint() { return point; }
    public void setPoint(int point) { this.point = point; }
}