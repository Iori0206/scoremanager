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

    public boolean save(List<TestScore> testList) throws Exception {
        String deleteSql =
            "DELETE FROM TEST WHERE STUDENT_NO = ? AND SUBJECT_CD = ? AND SCHOOL_CD = ? AND NO = ?";

        String insertSql =
            "INSERT INTO TEST (STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO, POINT) VALUES (?, ?, ?, ?, ?)";

        try (
            Connection con = getConnection();
            PreparedStatement deletePs = con.prepareStatement(deleteSql);
            PreparedStatement insertPs = con.prepareStatement(insertSql)
        ) {
            con.setAutoCommit(false);

            for (TestScore ts : testList) {
                if (ts.getPoint() < 0) {
                    continue;
                }

                deletePs.setString(1, ts.getStudent().getNo());
                deletePs.setString(2, ts.getSubject().getCd());
                deletePs.setString(3, ts.getSchool().getCd());
                deletePs.setInt(4, ts.getNum());
                deletePs.executeUpdate();

                insertPs.setString(1, ts.getStudent().getNo());
                insertPs.setString(2, ts.getSubject().getCd());
                insertPs.setString(3, ts.getSchool().getCd());
                insertPs.setInt(4, ts.getNum());
                insertPs.setInt(5, ts.getPoint());
                insertPs.executeUpdate();
            }

            con.commit();
            return true;
        }
    }

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

                    int testNo = rs.getInt("TEST_NO");
                    ts.setNum(rs.wasNull() ? 1 : testNo);

                    int point = rs.getInt("POINT");
                    ts.setPoint(rs.wasNull() ? -1 : point);

                    list.add(ts);
                }
            }
        }

        return list;
    }

    public List<TestScore> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<TestScore> list = new ArrayList<>();
        if (subject == null || school == null) {
            return list;
        }

        String sql =
            "SELECT S.NO AS STUDENT_NO, S.NAME, S.ENT_YEAR, S.CLASS_NUM, " +
            "       T1.POINT AS POINT1, T2.POINT AS POINT2 " +
            "FROM STUDENT S " +
            "LEFT JOIN TEST T1 ON T1.STUDENT_NO = S.NO " +
            "AND T1.SUBJECT_CD = ? " +
            "AND T1.SCHOOL_CD = ? " +
            "AND T1.NO = 1 " +
            "LEFT JOIN TEST T2 ON T2.STUDENT_NO = S.NO " +
            "AND T2.SUBJECT_CD = ? " +
            "AND T2.SCHOOL_CD = ? " +
            "AND T2.NO = 2 " +
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
            ps.setString(3, subject.getCd());
            ps.setString(4, school.getCd());
            ps.setInt(5, entYear);
            ps.setString(6, classNum);
            ps.setString(7, school.getCd());

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

                    int point1 = rs.getInt("POINT1");
                    if (rs.wasNull()) {
                        point1 = -1;
                    }

                    int point2 = rs.getInt("POINT2");
                    if (rs.wasNull()) {
                        point2 = -1;
                    }

                    ts.setPoint(point1);
                    ts.setPoint2(point2);

                    list.add(ts);
                }
            }
        }

        return list;
    }
}