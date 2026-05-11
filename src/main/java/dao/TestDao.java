package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;
import bean.TestScore;

public class TestDao extends DAO {

    /**
     * 【科目別成績取得】
     * 検索結果の各成績データに学生情報を紐づけるよう修正しました。
     */
    public List<TestScore> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<TestScore> list = new ArrayList<>();
        if (subject == null) return list;

        String sql = "SELECT S.NO AS STUDENT_NO, S.NAME, T.POINT " +
                     "FROM STUDENT S " +
                     "LEFT JOIN TEST T ON T.STUDENT_NO = S.NO " +
                     "AND T.SUBJECT_CD = ? " +
                     "WHERE S.ENT_YEAR = ? AND S.CLASS_NUM = ? AND S.SCHOOL_CD = ? " +
                     "ORDER BY S.NO";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, subject.getCd());
            ps.setInt(2, entYear);
            ps.setString(3, classNum);
            ps.setString(4, school.getCd());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TestScore ts = new TestScore();
                
                // --- 【修正箇所】JSPの ${t.student.name} 等に対応させるためオブジェクトを生成 ---
                Student s = new Student();
                s.setNo(rs.getString("STUDENT_NO"));
                s.setName(rs.getString("NAME"));
                s.setEntYear(entYear);
                s.setClassNum(classNum);
                
                // TestScoreにStudentをセット（これでNullPointerExceptionが消えます）
                ts.setStudent(s);
                
                // 直接値を保持する変数がある場合もセット
                ts.setStudentNo(rs.getString("STUDENT_NO"));
                ts.setStudentName(rs.getString("NAME"));
                
                int point = rs.getInt("POINT");
                if (rs.wasNull()) {
                    ts.setPoint(-1); // データがない場合は-1
                } else {
                    ts.setPoint(point);
                }
                list.add(ts);
            }
        }
        return list;
    }

    /**
     * 【成績保存】
     * MERGE文を使用して、存在すれば更新、なければ挿入します。
     */
    public boolean save(List<Test> testList) throws Exception {
        String sql = "MERGE INTO TEST (STUDENT_NO, SUBJECT_CD, SCHOOL_CD, POINT, CLASS_NUM) " +
                     "KEY(STUDENT_NO, SUBJECT_CD, SCHOOL_CD) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            for (Test test : testList) {
                ps.setString(1, test.getStudent().getNo());
                ps.setString(2, test.getSubject().getCd());
                ps.setString(3, test.getSchool().getCd());
                ps.setInt(4, test.getPoint());
                ps.setString(5, test.getStudent().getClassNum());
                ps.addBatch();
            }
            int[] results = ps.executeBatch();
            return results.length == testList.size();
        }
    }

    /**
     * 【学生別成績取得】
     */
    public List<Test> filter(Student student) throws Exception {
        List<Test> list = new ArrayList<>();
        String sql = "SELECT t.*, s.NAME AS SUBJECT_NAME " +
                     "FROM TEST t " +
                     "JOIN SUBJECT s ON t.SUBJECT_CD = s.CD AND t.SCHOOL_CD = s.SCHOOL_CD " +
                     "WHERE t.STUDENT_NO = ? AND t.SCHOOL_CD = ? " +
                     "ORDER BY t.SUBJECT_CD ASC";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, student.getNo());
            ps.setString(2, student.getSchool().getCd());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Test test = new Test();
                test.setPoint(rs.getInt("POINT"));
                
                Subject subject = new Subject();
                subject.setCd(rs.getString("SUBJECT_CD"));
                subject.setName(rs.getString("SUBJECT_NAME"));
                test.setSubject(subject);
                test.setStudent(student);

                list.add(test);
            }
        }
        return list;
    }
}