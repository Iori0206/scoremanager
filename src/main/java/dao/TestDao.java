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
     * 【科目別成績取得】 (ScoreListAction, TestListSubjectExecuteAction 用)
     * 引数は5つ (入学年度, クラス, 科目, 回数, 学校) です。
     */
    public List<TestScore> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<TestScore> list = new ArrayList<>();
        if (subject == null) return list;

        String sql = "SELECT s.no, s.name, t.point " +
                     "FROM student s " +
                     "LEFT JOIN test t ON t.student_no = s.no AND t.subject_cd = ? AND t.no = ? " +
                     "WHERE s.ent_year = ? AND s.class_num = ? AND s.school_cd = ? " +
                     "ORDER BY s.no";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, subject.getSubjectCd());
            ps.setInt(2, num);
            ps.setInt(3, entYear);
            ps.setString(4, classNum);
            ps.setString(5, school.getCd());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TestScore ts = new TestScore();
                ts.setStudentNo(rs.getString("no"));
                ts.setStudentName(rs.getString("name"));
                int point = rs.getInt("point");
                ts.setPoint(rs.wasNull() ? -1 : point); // 成績がなければ-1
                list.add(ts);
            }
        }
        return list;
    }

    /**
     * 【学生別成績取得】 (TestListStudentExecuteAction 用)
     * 引数は1つ (学生) です。
     */
    public List<Test> filter(Student student) throws Exception {
        List<Test> list = new ArrayList<>();
        String sql = "SELECT * FROM test WHERE student_no = ? ORDER BY subject_cd ASC, no ASC";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, student.getNo());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Test t = new Test();
                t.setNo(rs.getInt("no"));
                t.setPoint(rs.getInt("point"));
                // 必要に応じて科目情報を追加
                list.add(t);
            }
        }
        return list;
    }

    /**
     * 【成績保存】 (TestRegistExecuteAction 用)
     */
    public boolean save(List<Test> testList) throws Exception {
        String sql = "MERGE INTO test (student_no, subject_cd, school_cd, no, point) KEY(student_no, subject_cd, school_cd, no) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            for (Test test : testList) {
                ps.setString(1, test.getStudent().getNo());
                ps.setString(2, test.getSubject().getSubjectCd());
                ps.setString(3, test.getSchool().getCd());
                ps.setInt(4, test.getNo());
                ps.setInt(5, test.getPoint());
                ps.addBatch();
            }
            return ps.executeBatch().length == testList.size();
        }
    }
}