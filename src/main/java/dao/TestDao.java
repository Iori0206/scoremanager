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
     * 成績保存（TestSubjectExecuteAction の save エラーを解消）
     */
    public boolean save(List<TestScore> testList) throws Exception {
        // H2データベースのMERGE文を使用し、キーが一致すれば更新、なければ挿入
        String sql =
            "MERGE INTO TEST (STUDENT_NO, SUBJECT_CD, SCHOOL_CD, POINT, NO) " +
            "KEY(STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO) " +
            "VALUES (?, ?, ?, ?, ?)";

        try (
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            con.setAutoCommit(false);

            for (TestScore ts : testList) {
                // 点数が不正（未入力）な場合はスキップ
                if (ts.getPoint() < 0) continue;
                // 回数が0以下の場合はスキップ
                if (ts.getNum() <= 0) continue;

                ps.setString(1, ts.getStudent().getNo());
                ps.setString(2, ts.getSubject().getCd());
                ps.setString(3, ts.getSchool().getCd());
                ps.setInt(4, ts.getPoint());
                ps.setInt(5, ts.getNum()); // Beanに保持されている回数(1, 2など)をセット

                ps.addBatch();
            }

            ps.executeBatch();
            con.commit();
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 学生別成績取得（回数が0と表示される問題を解決）
     */
    public List<TestScore> filter(Student student) throws Exception {
        List<TestScore> list = new ArrayList<>();

        // T.NO に AS KAISU と別名をつけ、Java側で確実に取得できるようにします
        String sql =
            "SELECT T.POINT, T.NO AS KAISU, T.SUBJECT_CD, SUB.NAME AS SUBJECT_NAME " +
            "FROM TEST T " +
            "JOIN SUBJECT SUB ON T.SUBJECT_CD = SUB.CD AND T.SCHOOL_CD = SUB.SCHOOL_CD " +
            "WHERE T.STUDENT_NO = ? AND T.SCHOOL_CD = ? " +
            "ORDER BY T.SUBJECT_CD ASC, T.NO ASC";

        try (
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, student.getNo());
            ps.setString(2, student.getSchool().getCd());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TestScore ts = new TestScore();
                    ts.setPoint(rs.getInt("POINT"));
                    
                    // 別名「KAISU」で取得することで、0 になる現象を防ぎます
                    ts.setNum(rs.getInt("KAISU")); 

                    Subject subject = new Subject();
                    subject.setCd(rs.getString("SUBJECT_CD"));
                    subject.setName(rs.getString("SUBJECT_NAME"));

                    ts.setSubject(subject);
                    ts.setStudent(student);
                    ts.setSchool(student.getSchool());
                    list.add(ts);
                }
            }
        }
        return list;
    }

    /**
     * 科目別成績取得（ScoreSearchAction での引数不一致エラーを解消）
     */
    public List<TestScore> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<TestScore> list = new ArrayList<>();
        if (subject == null) return list;

        String sql =
            "SELECT S.NO AS STUDENT_NO, S.NAME, S.ENT_YEAR, S.CLASS_NUM, T.POINT " +
            "FROM STUDENT S " +
            "LEFT JOIN TEST T ON T.STUDENT_NO = S.NO " +
            "AND T.SUBJECT_CD = ? " +
            "AND T.SCHOOL_CD = ? " +
            "WHERE S.ENT_YEAR = ? AND S.CLASS_NUM = ? AND S.SCHOOL_CD = ? " +
            "ORDER BY S.NO ASC";

        try (
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, subject.getCd());
            ps.setString(2, school.getCd());
            ps.setInt(3, entYear);
            ps.setString(4, classNum);
            ps.setString(5, school.getCd());

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

                    int point = rs.getInt("POINT");
                    ts.setPoint(rs.wasNull() ? -1 : point);

                    list.add(ts);
                }
            }
        }
        return list;
    }
    
}