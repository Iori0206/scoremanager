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
     * ResultSetからStudentオブジェクトを作成する補助メソッド
     */
    private Student createStudent(ResultSet rs) throws Exception {
        Student s = new Student();
        s.setNo(rs.getString("no"));
        s.setName(rs.getString("name"));
        s.setEntYear(rs.getInt("ent_year"));
        s.setClassNum(rs.getString("class_num"));
        s.setAttend(rs.getBoolean("is_attend"));

        School sc = new School();
        sc.setCd(rs.getString("school_cd"));
        s.setSchool(sc);
        return s;
    }

    /**
     * ★追加：学校に所属する学生を全件取得（Actionからのエラーを解消）
     */
    public List<Student> filter(School school) throws Exception {
        // 下の4引数版filterを「条件なし」の状態で呼び出してエラーを回避する
        return filter(school, 0, null, false);
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
                list.add(createStudent(rs));
            }
        }
        return list;
    }

    /**
     * 入学年度一覧取得（0を除外するよう修正）
     */
    public List<Integer> filterEntYear(School school) throws Exception {
        List<Integer> list = new ArrayList<>();
        if (school == null || school.getCd() == null) return list;

        // ★ ent_year > 0 を追加して、不適切な「0」を表示させない
        String sql = "SELECT DISTINCT ent_year FROM student WHERE school_cd = ? AND ent_year > 0 ORDER BY ent_year DESC";
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
     * クラス一覧取得（空文字を除外するよう修正）
     */
    public List<String> filterClassNum(School school) throws Exception {
        List<String> list = new ArrayList<>();
        if (school == null || school.getCd() == null) return list;

        // ★ class_num が空でないものに絞る
        String sql = "SELECT DISTINCT class_num FROM student WHERE school_cd = ? AND class_num IS NOT NULL AND class_num <> '' ORDER BY class_num ASC";
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
     * 学生情報の登録または更新（save）
     */
    public boolean save(Student student) throws Exception {
        Student existing = get(student.getNo());
        if (existing == null) {
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
        } else {
            return update(student);
        }
    }

    /**
     * 学生変更（update）
     */
    public boolean update(Student student) throws Exception {
        String sql = "UPDATE student SET name = ?, ent_year = ?, class_num = ?, is_attend = ? WHERE no = ?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, student.getName());
            st.setInt(2, student.getEntYear());
            st.setString(3, student.getClassNum());
            st.setBoolean(4, student.isAttend());
            st.setString(5, student.getNo());
            return st.executeUpdate() == 1;
        }
    }

    /**
     * 学生1件取得（get）
     */
    public Student get(String no) throws Exception {
        Student student = null;
        String sql = "SELECT * FROM student WHERE no = ?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, no);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                student = createStudent(rs);
            }
        }
        return student;
    }
}