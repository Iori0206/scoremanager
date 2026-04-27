package bean;

import java.io.Serializable;

public class Test implements Serializable {
    
    // 学生
    private Student student;
    
    // 科目
    private Subject subject;
    
    // 学校
    private School school;
    
    // 回数
    private int no;
    
    // 得点
    private int point;
    
    // クラス番号
    private String classNum;

    /**
     * 学生情報の取得
     */
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    /**
     * 科目情報の取得
     */
    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    /**
     * 学校情報の取得
     */
    public School getSchool() { return school; }
    public void setSchool(School school) { this.school = school; }

    /**
     * テスト回数の取得
     */
    public int getNo() { return no; }
    public void setNo(int no) { this.no = no; }

    /**
     * 得点の取得
     */
    public int getPoint() { return point; }
    public void setPoint(int point) { this.point = point; }

    /**
     * クラス番号の取得
     */
    public String getClassNum() { return classNum; }
    public void setClassNum(String classNum) { this.classNum = classNum; }
}