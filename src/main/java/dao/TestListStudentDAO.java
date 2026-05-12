package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDAO extends DAO {

    /**
     * 指定された学生の成績一覧を取得します。
     */
    public List<TestListStudent> filter(Student student) throws Exception {
        List<TestListStudent> list = new ArrayList<>();

        // 【最終確定SQL】
        // 前の画像で TESTテーブルの「回数」が CLASS_NUM であることが分かりました。
        // なので、取得する列名は T.CLASS_NUM にします。
        String sql = "SELECT SUB.NAME, T.SUBJECT_CD, T.POINT, T.CLASS_NUM "
                   + "FROM TEST T "
                   + "INNER JOIN SUBJECT SUB ON T.SUBJECT_CD = SUB.CD AND T.SCHOOL_CD = SUB.SCHOOL_CD "
                   + "WHERE T.STUDENT_NO = ? AND T.SCHOOL_CD = ? "
                   + "ORDER BY T.SUBJECT_CD ASC";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            
            // student.getNo() で取得できる学籍番号（今回の画像だと 137 など）をセット
            st.setString(1, student.getNo().trim());
            st.setString(2, student.getSchool().getCd());

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    TestListStudent tls = new TestListStudent();
                    
                    tls.setSubjectName(rs.getString("NAME"));
                    tls.setSubjectCd(rs.getString("SUBJECT_CD"));
                    tls.setPoint(rs.getInt("POINT"));
                    
                    // 【重要】DBのカラム名 CLASS_NUM から値を取り出し、
                    // Beanの Num（回数）にセットします。
                    tls.setNum(rs.getInt("CLASS_NUM"));

                    list.add(tls);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return list;
    }
}