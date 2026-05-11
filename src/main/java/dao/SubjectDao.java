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
     * 科目一覧取得
     */
    public List<Subject> filter(School school) throws Exception {
        List<Subject> list = new ArrayList<>();

        if (school == null) {
            return list;
        }

        String sql = "SELECT * FROM subject WHERE school_cd = ? ORDER BY cd";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, school.getCd());

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Subject subject = new Subject();
                    subject.setSchool(school);
                    subject.setCd(rs.getString("cd"));
                    subject.setName(rs.getString("name"));
                    list.add(subject);
                }
            }
        }

        return list;
    }

    /**
     * 科目1件取得
     */
    public Subject get(School school, String cd) throws Exception {
        Subject subject = null;

        if (school == null || cd == null || cd.isEmpty()) {
            return null;
        }

        String sql = "SELECT * FROM subject WHERE school_cd = ? AND cd = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, school.getCd());
            st.setString(2, cd);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    subject = new Subject();
                    subject.setSchool(school);
                    subject.setCd(rs.getString("cd"));
                    subject.setName(rs.getString("name"));
                }
            }
        }

        return subject;
    }

    /**
     * 科目新規登録
     */
    public int insert(Subject subject) throws Exception {
        String sql = "INSERT INTO subject (school_cd, cd, name) VALUES (?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, subject.getSchool().getCd());
            st.setString(2, subject.getCd());
            st.setString(3, subject.getName());

            return st.executeUpdate();
        }
    }

    /**
     * 科目更新
     */
    public int update(Subject subject) throws Exception {
        String sql = "UPDATE subject SET name = ? WHERE school_cd = ? AND cd = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, subject.getName());
            st.setString(2, subject.getSchool().getCd());
            st.setString(3, subject.getCd());

            return st.executeUpdate();
        }
    }

    /**
     * 科目削除
     */
    public int delete(School school, String cd) throws Exception {
        String sql = "DELETE FROM subject WHERE school_cd = ? AND cd = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, school.getCd());
            st.setString(2, cd);

            return st.executeUpdate();
        }
    }

    /**
     * 登録か更新か自動判定
     */
    public int save(Subject subject) throws Exception {
        Subject old = get(subject.getSchool(), subject.getCd());

        if (old == null) {
            return insert(subject);
        } else {
            return update(subject);
        }
    }
}