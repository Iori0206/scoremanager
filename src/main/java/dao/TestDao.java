package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.TestScore;

public class TestDao extends DAO {

    /**
     * 成績保存
     */
    public boolean save(List<TestScore> testList) throws Exception {
        String sql =
            "MERGE INTO TEST (STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO, POINT) " +
            "KEY(STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO) " +
            "VALUES (?, ?, ?, ?, ?)";

        try (
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            con.setAutoCommit(false);

            for (TestScore ts : testList) {
                if (ts.getPoint() < 0) {
                    continue;
                }

                ps.setString(1, ts.getStudent().getNo());
                ps.setString(2, ts.getSubject().getCd());
                ps.setString(3, ts.getSchool().getCd());
                ps.setInt(4, ts.getNum());
                ps.setInt(5, ts.getPoint());
                ps.addBatch();
            }

            ps.executeBatch();
            con.commit();
            return true;
        }
    }

    /**
     * 学生別成績取得
     * 科目×回数で表示
     */
    public List<TestScore> filter(Student student) throws Exception {
        List<TestScore> list = new ArrayList<>();

        if (student == null || student.getNo() == null || student.getSchool() == null) {
            return list;
        }

        String sql =
            "SELECT SUB.CD AS SUBJECT_CD, SUB.NAME AS SUBJECT_NAME, " +
            "       T.NO AS TEST_NO, T.POINT " +
            "FROM SUBJECT SUB " +
            "LEFT JOIN TEST T ON T.SUBJECT_CD = SUB.CD " +
            "AND T.STUDENT_NO = ? " +
            "AND T.SCHOOL_CD = SUB.SCHOOL_CD " +
            "WHERE SUB.SCHOOL_CD = ? " +
            "ORDER BY SUB.CD ASC, T.NO ASC";

        try (
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, student.getNo());
            ps.setString(2, student.getSchool().getCd());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TestScore ts = new TestScore();

                    Subject subject = new Subject();
                    subject.setCd(rs.getString("SUBJECT_CD"));
                    subject.setName(rs.getString("SUBJECT_NAME"));

                    ts.setStudent(student);
                    ts.setSubject(subject);
                    ts.setSchool(student.getSchool());

                    int no = rs.getInt("TEST_NO");
                    ts.setNum(rs.wasNull() ? 1 : no);

                    int point = rs.getInt("POINT");
                    ts.setPoint(rs.wasNull() ? -1 : point);

                    list.add(ts);
                }
            }
        }

        return list;
    }

    /**
     * 科目別成績取得
     */
    public List<TestScore> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<TestScore> list = new ArrayList<>();
        if (subject == null || school == null) {
            return list;
        }

        String sql =
            "SELECT S.NO AS STUDENT_NO, S.NAME, S.ENT_YEAR, S.CLASS_NUM, T.POINT, T.NO AS TEST_NO " +
            "FROM STUDENT S " +
            "LEFT JOIN TEST T ON T.STUDENT_NO = S.NO " +
            "AND T.SUBJECT_CD = ? " +
            "AND T.SCHOOL_CD = ? " +
            "AND T.NO = ? " +
            "WHERE S.ENT_YEAR = ? " +
            "AND S.CLASS_NUM = ? " +
            "AND S.SCHOOL_CD = ? " +
            "ORDER BY S.NO ASC";

        try (
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, subject.getCd());
            ps.setString(2, school.getCd());
            ps.setInt(3, num);
            ps.setInt(4, entYear);
            ps.setString(5, classNum);
            ps.setString(6, school.getCd());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TestScore ts = new TestScore();

                    Student s = new Student();
                    s.setNo(rs.getString("STUDENT_NO"));
                    s.setName(rs.getString("NAME"));
                    s.setEntYear(rs.getInt("ENT_YEAR"));
                    s.setClassNum(rs.getString("CLASS_NUM"));
                    s.setSchool(school);

                    ts.setStudent(s);
                    ts.setSubject(subject);
                    ts.setSchool(school);

                    int testNo = rs.getInt("TEST_NO");
                    ts.setNum(rs.wasNull() ? num : testNo);

                    int point = rs.getInt("POINT");
                    ts.setPoint(rs.wasNull() ? -1 : point);

                    list.add(ts);
                }
            }
        }

        return list;
    }
}