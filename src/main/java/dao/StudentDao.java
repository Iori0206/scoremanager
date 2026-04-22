package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;

public class StudentDao extends DAO {

    // 学生一覧の絞り込み
    public List<Student> filter(String entYear, String classNum, String isAttend) throws Exception {

        List<Student> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
            "SELECT no, name, ent_year, class_num, is_attend, school_cd FROM student WHERE 1=1"
        );

        if (entYear != null && !entYear.isEmpty()) {
            sql.append(" AND ent_year = ?");
        }
        if (classNum != null && !classNum.isEmpty()) {
            sql.append(" AND class_num = ?");
        }
        if ("1".equals(isAttend)) {
            sql.append(" AND is_attend = true");
        }

        sql.append(" ORDER BY no");

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql.toString())) {

            int idx = 1;
            if (entYear != null && !entYear.isEmpty()) st.setInt(idx++, Integer.parseInt(entYear));
            if (classNum != null && !classNum.isEmpty()) st.setString(idx++, classNum);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Student s = new Student();
                s.setNo(rs.getString("no"));
                s.setName(rs.getString("name"));
                s.setEntYear(rs.getInt("ent_year"));
                s.setClassNum(rs.getString("class_num"));
                s.setAttend(rs.getBoolean("is_attend"));
                // school_cd は一覧では使わないのでセット不要
                list.add(s);
            }
        }

        return list;
    }

    // 学生の新規登録
    public boolean save(Student student) throws Exception {

        String sql = "INSERT INTO student (no, name, ent_year, class_num, is_attend, school_cd) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

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
}