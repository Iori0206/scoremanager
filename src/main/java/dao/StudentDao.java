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
     * 学生一覧の絞り込み
     */
    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
        List<Student> list = new ArrayList<>();
        if (school == null) return list;

        // 【デバッグ用】DBのデータ（tes）と強制的に一致させます。
        // 画面にデータが出ることが確認できたら、この一行を消してください。
        school.setCd("tes"); 

        StringBuilder sql = new StringBuilder("SELECT * FROM student WHERE school_cd = ?");

        if (entYear != 0) {
            sql.append(" AND ent_year = ?");
        }
        if (classNum != null && !classNum.isEmpty() && !classNum.equals("------")) {
            sql.append(" AND class_num = ?");
        }
        if (isAttend) {
            // H2コンソールの画像に合わせて小文字の true で判定
            sql.append(" AND is_attend = true");
        }
        sql.append(" ORDER BY no ASC");

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql.toString())) {
            
            int idx = 1;
            st.setString(idx++, school.getCd());
            
            if (entYear != 0) {
                st.setInt(idx++, entYear);
            }
            if (classNum != null && !classNum.isEmpty() && !classNum.equals("------")) {
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
                list.add(s);
            }
        }
        return list;
    }

    /**
     * 学生の新規登録
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
     * 学生情報を1件取得
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
            }
        }
        return student;
    }
}