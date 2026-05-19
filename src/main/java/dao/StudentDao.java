package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends DAO {

    /**
     * 学校に所属する学生を全件取得します
     */
    public List<Student> filter(School school) throws Exception {
        List<Student> list = new ArrayList<>();
        if (school == null || school.getCd() == null) {
            return list;
        }

        String sql = "SELECT * FROM student WHERE school_cd = ? ORDER BY no ASC";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, school.getCd());
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Student s = new Student();
                s.setNo(rs.getString("no"));
                s.setName(rs.getString("name"));
                s.setEntYear(rs.getInt("ent_year"));
                s.setClassNum(rs.getString("class_num"));
                s.setAttend(rs.getBoolean("is_attend"));

                School sc = new School();
                sc.setCd(rs.getString("school_cd"));
                s.setSchool(sc);

                list.add(s);
            }
        }
        return list;
    }

    /**
     * 学生一覧検索（詳細条件あり）
     */
    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
        List<Student> list = new ArrayList<>();
        if (school == null || school.getCd() == null) {
            return list;
        }

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM student WHERE school_cd = ? ");

        if (entYear != 0) {
            sql.append("AND ent_year = ? ");
        }
        if (classNum != null && !classNum.isEmpty()) {
            sql.append("AND class_num = ? ");
        }
        if (isAttend) {
            sql.append("AND is_attend = true ");
        }
        sql.append("ORDER BY no ASC");

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql.toString())) {

            int idx = 1;
            st.setString(idx++, school.getCd());

            if (entYear != 0) {
                st.setInt(idx++, entYear);
            }
            if (classNum != null && !classNum.isEmpty()) {
                st.setString(idx++, classNum);
            }

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Student s = new Student();
                s.setNo(rs.getString("no"));
                s.setName(rs.getString("name"));
                s.setEntYear(rs.getInt("ent_year"));
                s.setClassNum(rs.getString("class_num"));
                s.setAttend(rs.getBoolean("is_attend"));

                School sc = new School();
                sc.setCd(rs.getString("school_cd"));
                s.setSchool(sc);

                list.add(s);
            }
        }
        return list;
    }

    /**
     * 入学年度一覧取得
     */
    public List<Integer> filterEntYear(School school) throws Exception {
        List<Integer> list = new ArrayList<>();
        if (school == null || school.getCd() == null) return list;

        String sql = "SELECT DISTINCT ent_year FROM student WHERE school_cd = ? ORDER BY ent_year DESC";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, school.getCd());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("ent_year"));
            }
        }
        return list;
    }

    /**
     * クラス一覧取得
     */
    public List<String> filterClassNum(School school) throws Exception {
        List<String> list = new ArrayList<>();
        if (school == null || school.getCd() == null) return list;

        String sql = "SELECT DISTINCT class_num FROM student WHERE school_cd = ? ORDER BY class_num ASC";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, school.getCd());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("class_num"));
            }
        }
        return list;
    }

    /**
     * 学生保存（登録・更新）
     * ★重複エラー対策：既存データがあればUPDATE、なければINSERTを実行
     */
    public boolean save(Student student) throws Exception {
        // 既存データの有無を確認
        Student existing = get(student.getNo());

        try (Connection con = getConnection()) {
            String sql;
            PreparedStatement st;

            if (existing == null) {
                // --- 存在しない場合は新規登録 (INSERT) ---
                sql = "INSERT INTO student (no, name, ent_year, class_num, is_attend, school_cd) VALUES (?, ?, ?, ?, ?, ?)";
                st = con.prepareStatement(sql);
                st.setString(1, student.getNo());
                st.setString(2, student.getName());
                st.setInt(3, student.getEntYear());
                st.setString(4, student.getClassNum());
                st.setBoolean(5, student.isAttend());
                st.setString(6, student.getSchool().getCd());
            } else {
                // --- 存在する場合は更新 (UPDATE) ---
                // WHERE句に no と school_cd を指定して特定
                sql = "UPDATE student SET name = ?, ent_year = ?, class_num = ?, is_attend = ? WHERE no = ? AND school_cd = ?";
                st = con.prepareStatement(sql);
                st.setString(1, student.getName());
                st.setInt(2, student.getEntYear());
                st.setString(3, student.getClassNum());
                st.setBoolean(4, student.isAttend());
                st.setString(5, student.getNo());
                st.setString(6, student.getSchool().getCd());
            }

            int count = st.executeUpdate();
            st.close();

            return count > 0;
        }
    }

    /**
     * 学生1件取得
     */
    public Student get(String no) throws Exception {
        Student student = null;
        String sql = "SELECT * FROM student WHERE no = ?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, no);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                student = new Student();
                student.setNo(rs.getString("no"));
                student.setName(rs.getString("name"));
                student.setEntYear(rs.getInt("ent_year"));
                student.setClassNum(rs.getString("class_num"));
                student.setAttend(rs.getBoolean("is_attend"));
                
                School school = new School();
                school.setCd(rs.getString("school_cd"));
                student.setSchool(school);
            }
        }
        return student;
    }
}