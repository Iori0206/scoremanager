package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends DAO {

    /**
     * 科目1件取得 (SubjectUpdateActionなどで使用)
     */
    public Subject get(String cd, School school) throws Exception {
        if (school == null || cd == null) return null;
        Subject subject = null;
        String sql = "SELECT * FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cd);
            ps.setString(2, school.getCd());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                subject = new Subject();
                subject.setSubjectCd(rs.getString("CD"));
                subject.setName(rs.getString("NAME"));
            }
        }
        return subject;
    }

    /**
     * 科目一覧取得 (SubjectListActionなどで使用)
     */
    public List<Subject> filter(School school) throws Exception {
        List<Subject> list = new ArrayList<>();
        if (school == null) return list;
        String sql = "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ? ORDER BY CD";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, school.getCd());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Subject s = new Subject();
                s.setSubjectCd(rs.getString("CD"));
                s.setName(rs.getString("NAME"));
                list.add(s);
            }
        }
        return list;
    }

    /**
     * 保存・更新処理 (SubjectUpdateExecuteActionなどで使用)
     */
    public void save(Subject subject, School school) throws Exception {
        // 重複があれば更新、なければ挿入するSQL例（H2データベース用）
        String sql = "MERGE INTO SUBJECT (CD, NAME, SCHOOL_CD) KEY(CD, SCHOOL_CD) VALUES (?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, subject.getSubjectCd());
            ps.setString(2, subject.getName());
            ps.setString(3, school.getCd());
            ps.executeUpdate();
        }
    }

    /**
     * 削除処理 (SubjectDeleteExecuteActionなどで使用)
     */
    public boolean delete(Subject subject, School school) throws Exception {
        String sql = "DELETE FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, subject.getSubjectCd());
            ps.setString(2, school.getCd());
            return ps.executeUpdate() > 0;
        }
    }
}