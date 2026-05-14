package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.TestScore;

public class TestListStudentDAO extends DAO {

    public List<TestScore> filter(Student student) throws Exception {
        List<TestScore> list = new ArrayList<>();

        String sql =
            "SELECT SUB.NAME, T.SUBJECT_CD, T.POINT, T.NO, S.CLASS_NUM " +
            "FROM TEST T " +
            "INNER JOIN SUBJECT SUB ON T.SUBJECT_CD = SUB.CD AND T.SCHOOL_CD = SUB.SCHOOL_CD " +
            "INNER JOIN STUDENT S ON T.STUDENT_NO = S.NO AND T.SCHOOL_CD = S.SCHOOL_CD " +
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

                    Subject subject = new Subject();
                    subject.setCd(rs.getString("SUBJECT_CD"));
                    subject.setName(rs.getString("NAME"));

                    student.setClassNum(rs.getString("CLASS_NUM"));

                    ts.setStudent(student);
                    ts.setSubject(subject);
                    ts.setSchool(student.getSchool());
                    ts.setNum(rs.getInt("NO"));
                    ts.setPoint(rs.getInt("POINT"));

                    list.add(ts);
                }
            }
        }

        return list;
    }
}