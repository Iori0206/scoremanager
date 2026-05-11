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
     * 学生一覧検索
     * @param school 学校
     * @param entYear 入学年度 (0の場合は全年度)
     * @param classNum クラス番号 (null/空文字の場合は全クラス)
     * @param isAttend 在学中フラグ (true:在学中のみ, false:全て)
     * @return 学生リスト
     */
    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
        List<Student> list = new ArrayList<>();
        if (school == null || school.getCd() == null) {
            return list;
        }
 
        // 1. SQLの組み立て（WHERE 1=1 を使うとANDの結合が楽になります）
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM student WHERE school_cd = ? ");
 
        if (entYear != 0) {
            sql.append("AND ent_year = ? ");
        }
        if (classNum != null && !classNum.isEmpty()) {
            sql.append("AND class_num = ? ");
        }
        if (isAttend) {
            // チェックが入っている時だけ「在学中(true)」で絞り込む
            // チェックがない時はこの条件を追加しないので、全件(true/false両方)が出る
            sql.append("AND is_attend = true ");
        }
        sql.append("ORDER BY no ASC");
 
        // --- デバッグログ（原因調査に必須！） ---
        System.out.println("----- DAO Debug -----");
        System.out.println("SQL: " + sql.toString());
        System.out.println("school_cd: " + school.getCd());
        System.out.println("ent_year: " + entYear);
        System.out.println("class_num: " + classNum);
        System.out.println("is_attend: " + isAttend);
        System.out.println("---------------------");
 
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql.toString())) {
 
            // 2. プレースホルダ(?)への値セット
            int idx = 1;
            st.setString(idx++, school.getCd());
 
            if (entYear != 0) {
                st.setInt(idx++, entYear);
            }
            if (classNum != null && !classNum.isEmpty()) {
                st.setString(idx++, classNum);
            }
 
            // 3. 実行
            ResultSet rs = st.executeQuery();
 
            // 4. 結果の詰め替え
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
     * 学生登録
     */
    public boolean save(Student student) throws Exception {
        String sql = "INSERT INTO student (no, name, ent_year, class_num, is_attend, school_cd) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, student.getNo());
            st.setString(2, student.getName());
            st.setInt(3, student.getEntYear());
            st.setString(4, student.getClassNum());
            st.setBoolean(5, student.isAttend());
            st.setString(6, student.getSchool().getCd());
            return st.executeUpdate() == 1;
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
 