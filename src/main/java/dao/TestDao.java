package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.TestScore;

public class TestDao extends DAO {

    public List<TestScore> getScoreList(String entYear, String classNum, String subjectCd, School school) throws Exception {

        List<TestScore> list = new ArrayList<>();

        String sql = """
            SELECT 
                s.no AS student_no,
                s.name AS student_name,
                t.point
            FROM student s
            LEFT JOIN test t
                ON t.student_no = s.no
                AND t.subject_cd = ?
            WHERE s.ent_year = ?
              AND s.class_num = ?
              AND s.school_cd = ?
            ORDER BY s.no
        """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, subjectCd);
            ps.setString(2, entYear);
            ps.setString(3, classNum);
            ps.setString(4, school.getCd());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TestScore ts = new TestScore();
                ts.setStudentNo(rs.getString("student_no"));
                ts.setStudentName(rs.getString("student_name"));
                ts.setPoint(rs.getInt("point"));
                list.add(ts);
            }
        }

        return list;
    }
}