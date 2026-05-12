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
     * 学校に所属する全学生を取得
     */
    public List<Student> filter(School school) throws Exception {
        List<Student> list = new ArrayList<>();
        // --- 修正点: nullチェック ---
        if (school == null) return list;

        String sql = "SELECT * FROM student WHERE school_cd = ? ORDER BY no ASC";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, school.getCd());
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(mapToStudent(rs, school));
                }
            }
        }
        return list;
    }

    /**
     * 学生一覧検索（詳細条件あり）
     */
    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
        List<Student> list = new ArrayList<>();
        // --- 修正点: nullチェック ---
        if (school == null) return list;

        StringBuilder sql = new StringBuilder("SELECT * FROM student WHERE school_cd = ? ");
        if (entYear != 0) sql.append("AND ent_year = ? ");
        if (classNum != null && !classNum.isEmpty()) sql.append("AND class_num = ? ");
        if (isAttend) sql.append("AND is_attend = true ");
        sql.append("ORDER BY no ASC");

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql.toString())) {

            int idx = 1;
            st.setString(idx++, school.getCd());
            if (entYear != 0) st.setInt(idx++, entYear);
            if (classNum != null && !classNum.isEmpty()) st.setString(idx++, classNum);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(mapToStudent(rs, school));
                }
            }
        }
        return list;
    }

    /**
     * 入学年度一覧取得
     */
    public List<Integer> filterEntYear(School school) throws Exception {
        List<Integer> list = new ArrayList<>();
        // --- 【重要修正】ここでNullPointerExceptionが発生していました ---
        if (school == null) return list;

        String sql = "SELECT DISTINCT ent_year FROM student WHERE school_cd = ? ORDER BY ent_year DESC";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, school.getCd());
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("ent_year"));
                }
            }
        }
        return list;
    }

    /**
     * クラス一覧取得
     */
    public List<String> filterClassNum(School school) throws Exception {
        List<String> list = new ArrayList<>();
        // --- 【重要修正】ここも同様にガード句を追加 ---
        if (school == null) return list;

        String sql = "SELECT DISTINCT class_num FROM student WHERE school_cd = ? ORDER BY class_num ASC";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, school.getCd());
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("class_num"));
                }
            }
        }
        return list;
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
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    School sc = new School();
                    sc.setCd(rs.getString("school_cd"));
                    student = mapToStudent(rs, sc);
                }
            }
        }
        return student;
    }

    /**
     * マッピング処理
     */
    private Student mapToStudent(ResultSet rs, School school) throws Exception {
        Student s = new Student();
        s.setNo(rs.getString("no"));
        s.setName(rs.getString("name"));
        s.setEntYear(rs.getInt("ent_year"));
        s.setClassNum(rs.getString("class_num"));
        s.setAttend(rs.getBoolean("is_attend"));
        s.setSchool(school);
        return s;
    }

    /**
     * 保存または更新
     */
    public boolean save(Student student) throws Exception {
        // --- 修正点: schoolがnullの場合、保存できないため早期リターン ---
        if (student == null || student.getSchool() == null) return false;

        Student existing = get(student.getNo());
        String sql;
        
        if (existing == null) {
            sql = "INSERT INTO student (name, ent_year, class_num, is_attend, school_cd, no) VALUES (?, ?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE student SET name=?, ent_year=?, class_num=?, is_attend=?, school_cd=? WHERE no=?";
        }

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, student.getName());
            st.setInt(2, student.getEntYear());
            st.setString(3, student.getClassNum());
            st.setBoolean(4, student.isAttend());
            st.setString(5, student.getSchool().getCd());
            st.setString(6, student.getNo());
            
            return st.executeUpdate() > 0;
        }
    }
}